package com.orbitsoft.cryptocurrencyrates.data.local;

import androidx.paging.PagingSource;

import com.orbitsoft.cryptocurrencyrates.data.Order;
import com.orbitsoft.cryptocurrencyrates.data.db.AppDatabase;
import com.orbitsoft.cryptocurrencyrates.data.local.model.CoinEntity;
import com.orbitsoft.cryptocurrencyrates.data.local.model.RemoteOffsetEntity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.rxjava3.core.Single;

@Singleton
public class LocalDataSource {
    private final AppDatabase db;
    private final DbDao dao;

    @Inject
    public LocalDataSource(AppDatabase db) {
        this.db = db;
        this.dao = db.coinsDao();
    }

    /**
     * Retrieve remote offset for order
     *
     * @param order
     * @return
     */
    public Single<RemoteOffsetEntity> remoteOffsetByOrder(@NotNull Order order) {
        return dao.remoteOffsetByOrder(order.getOrder(), order.getOrderDirection())
                .onErrorReturn(throwable -> new RemoteOffsetEntity(0, order));
    }

    /**
     * Insert list of coins to cache.Cache will be cleared before inserting if needed.
     *
     * @param coins          list of coins
     * @param nextOffset     next remote offset
     * @param withClearCache to trigger cache clear before inserting
     */
    public void insertCoins(@NotNull List<CoinEntity> coins,
                            @NotNull RemoteOffsetEntity nextOffset,
                            boolean withClearCache) {
        if (coins.size() == 0) return;
        Order order = new Order(coins.get(0).getOrder(), coins.get(0).getOrderDirection());
        db.runInTransaction(() -> {
            if (withClearCache) clearCache(order);

            // Update RemoteKey for this query.
            dao.insertOrReplaceRemoteOffset(nextOffset);
            dao.insertCoins(coins);
        });
    }

    /**
     * Clear cache for order
     *
     * @param order
     */
    private void clearCache(@NotNull Order order) {
        dao.clearCacheForOrder(order.getOrder(), order.getOrderDirection());
        dao.remoteOffsetByOrder(order.getOrder(), order.getOrderDirection());
    }

    public PagingSource<Integer, CoinEntity> load(@NotNull Order order) {
        PagingSource<Integer, CoinEntity> integerCoinEntityPagingSource = dao.pagingSourceByOrder(order.getOrder(), order.getOrderDirection());
        return integerCoinEntityPagingSource;
    }

    /**
     * Get oldest time when coin is added to cache for order
     *
     * @param order
     * @return
     */
    public Single<Long> getAddedAtOrder(@NotNull Order order) {
        return dao.getAddedAtOrder(order.getOrder(), order.getOrderDirection())
                .onErrorReturn(throwable -> -1L);
    }

    /**
     * Fully clear cache (remoteOffset and coins)
     */
    public void clearCache() {
        dao.clearAllCache();
        dao.clearRemoteOffset();
    }
}
