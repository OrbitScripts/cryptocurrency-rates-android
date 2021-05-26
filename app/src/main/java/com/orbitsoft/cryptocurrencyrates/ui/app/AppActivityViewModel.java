package com.orbitsoft.cryptocurrencyrates.ui.app;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.PagingDataTransforms;
import androidx.paging.rxjava3.PagingRx;

import com.orbitsoft.cryptocurrencyrates.AppExecutors;
import com.orbitsoft.cryptocurrencyrates.data.CoinsRemoteMediation;
import com.orbitsoft.cryptocurrencyrates.data.Order;
import com.orbitsoft.cryptocurrencyrates.data.local.LocalDataSource;
import com.orbitsoft.cryptocurrencyrates.data.local.model.CoinEntity;
import com.orbitsoft.cryptocurrencyrates.data.remote.CoinrankingSource;
import com.orbitsoft.cryptocurrencyrates.ui.list.ListItem;
import com.orbitsoft.cryptocurrencyrates.ui.model.CoinUi;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

@HiltViewModel
public class AppActivityViewModel extends ViewModel {

    private static final int PAGE_SIZE = 30;
    private static final int INITIAL_LOAD_SIZE = 50;
    private static final int PREFETCH_DISTANCE = 10;

    private final BehaviorSubject<Order> orderSubject = BehaviorSubject.createDefault(Order.buildDefault());
    private final BehaviorSubject<Flowable<PagingData<ListItem>>> pagingDataSubject = BehaviorSubject.create();
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    public BehaviorSubject<Order> getOrderSubject() {
        return orderSubject;
    }

    public BehaviorSubject<Flowable<PagingData<ListItem>>> getPagingDataSubject() {
        return pagingDataSubject;
    }

    @Inject
    public AppActivityViewModel(@NotNull CoinrankingSource remote,
                                @NotNull LocalDataSource local) {
        //Create new flowable on order changed
        compositeDisposable.add(
                orderSubject.subscribe(order -> {
                    Pager<Integer, CoinEntity> pager = new Pager<>(
                            new PagingConfig(PAGE_SIZE, PREFETCH_DISTANCE, false, INITIAL_LOAD_SIZE),
                            null, //initialKey
                            new CoinsRemoteMediation(remote, local, order),
                            () -> local.load(order)
                    );

                    //Convert paging data from data source to UI paging data
                    Flowable<PagingData<ListItem>> flowable = PagingRx.getFlowable(pager)
                            .map(pagingData -> PagingDataTransforms.map(pagingData, AppExecutors.networkIO(),
                                    entity -> new ListItem.Row(CoinUi.fromEntity(entity))));

                    Flowable<PagingData<ListItem>> pagingDataFlowable = PagingRx.cachedIn(flowable,
                            ViewModelKt.getViewModelScope(AppActivityViewModel.this));
                    pagingDataSubject.onNext(pagingDataFlowable);
                })
        );
    }

    /**
     * Set new order
     *
     * @param order new order
     */
    public void onOrderChanged(Order order) {
        orderSubject.onNext(order);
    }


    /**
     * Generate paging data with empty state
     *
     * @return Return paging data with empty state
     */
    public PagingData<ListItem> getEmptyStatePagingData() {
        return PagingData.from(List.of(new ListItem.EmptyState()));
    }

    /**
     * Init reload list.
     */
    public void reload() {
        onOrderChanged(
                orderSubject.getValue() != null ? orderSubject.getValue() : Order.buildDefault()
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
