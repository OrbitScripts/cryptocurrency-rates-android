package com.orbitsoft.cryptocurrencyrates.data;

import androidx.paging.LoadType;
import androidx.paging.PagingState;
import androidx.paging.rxjava3.RxRemoteMediator;

import com.orbitsoft.cryptocurrencyrates.data.local.LocalDataSource;
import com.orbitsoft.cryptocurrencyrates.data.local.model.CoinEntity;
import com.orbitsoft.cryptocurrencyrates.data.local.model.RemoteOffsetEntity;
import com.orbitsoft.cryptocurrencyrates.data.remote.CoinrankingSource;
import com.orbitsoft.cryptocurrencyrates.data.remote.model.CoinsResponse;

import org.jetbrains.annotations.NotNull;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * Used for incremental loading data from remote data source when local data is ended.
 */
public class CoinsRemoteMediation extends RxRemoteMediator<Integer, CoinEntity> {

    private static final int CACHE_TTL = 3600000; //1 hour

    private final CoinrankingSource remote;

    private final Order order;
    private final LocalDataSource local;

    public CoinsRemoteMediation(@NotNull CoinrankingSource remote,
                                @NotNull LocalDataSource local,
                                @NotNull Order order) {
        this.remote = remote;
        this.order = order;
        this.local = local;
    }

    @NotNull
    @Override
    public Single<InitializeAction> initializeSingle() {
        return local.getAddedAtOrder(order)
                .subscribeOn(Schedulers.io())
                .map(addedAt -> {
                    if (System.currentTimeMillis() - addedAt <= CACHE_TTL) {
                        // Cached data is up-to-date, so there is no need to re-fetch
                        // from the network.
                        return InitializeAction.SKIP_INITIAL_REFRESH;
                    } else {
                        // Need to refresh cached data from network;
                        return InitializeAction.LAUNCH_INITIAL_REFRESH;
                    }
                });
    }

    @NotNull
    @Override
    public Single<MediatorResult> loadSingle(
            @NotNull LoadType loadType,
            @NotNull PagingState<Integer, CoinEntity> pagingState) {
        // The network load method takes offset parameter. For every page
        // after the first, pass the offset returned from the previous page to
        // let it continue from where it left off. For REFRESH, pass 0 to load the
        // first page.
        Single<RemoteOffsetEntity> remoteOffsetSingle = null;
        switch (loadType) {
            case REFRESH:
                // Initial load should use 0 as the offset key, so you can return 0
                // directly.
                remoteOffsetSingle = Single.just(new RemoteOffsetEntity(0, order));
                break;
            case PREPEND:
                // we never need to prepend, since REFRESH will always
                // load the first page in the list. Immediately return, reporting end of
                // pagination.
                return Single.just(new MediatorResult.Success(true));
            case APPEND:
                // Query remoteOffsetSingle for the next RemoteKey.
                remoteOffsetSingle = local.remoteOffsetByOrder(order);
                break;
        }


        return remoteOffsetSingle
                .subscribeOn(Schedulers.io())
                .flatMap((Function<RemoteOffsetEntity, Single<MediatorResult>>) remoteOffset -> {
                    // null is only valid for initial load when appending. If we receive null
                    // for APPEND, that means we have reached the end of pagination
                    if (loadType != LoadType.REFRESH && remoteOffset == null) {
                        return Single.just(new MediatorResult.Success(true));
                    }
                    int limit = (remoteOffset.getNextOffset() == 0) ?
                            pagingState.getConfig().initialLoadSize : pagingState.getConfig().pageSize;
                    return remote.loadCoins(remoteOffset.getNextOffset(),
                            limit, order)
                            .subscribeOn(Schedulers.io())
                            .map((Function<CoinsResponse, MediatorResult>) response -> {
                                int nextOffset = remoteOffset.getNextOffset() + response.getData().getCoins().size();
                                // Insert new rows into database, which invalidates the current
                                // PagingData, allowing Paging to present the updates in the DB.
                                local.insertCoins(
                                        CoinEntity.fromRemoteList(response.getData().getCoins(),
                                                order, remoteOffset.getNextOffset()),
                                        new RemoteOffsetEntity(
                                                nextOffset,
                                                order
                                        ),
                                        loadType == LoadType.REFRESH
                                );
                                return new MediatorResult.Success(response.getData().getCoins().size() == 0 ||
                                        nextOffset >= response.getData().getStats().getTotal());
                            })
                            .onErrorResumeNext(throwable -> Single.just(new MediatorResult.Error(throwable)));
                });
    }
}
