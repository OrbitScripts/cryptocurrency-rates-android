package com.orbitsoft.cryptocurrencyrates.ui.app;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.LoadState;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.orbitsoft.cryptocurrencyrates.R;
import com.orbitsoft.cryptocurrencyrates.databinding.ActivityAppBinding;
import com.orbitsoft.cryptocurrencyrates.ui.list.CoinrankingListAdapter;
import com.orbitsoft.cryptocurrencyrates.ui.list.ListItemDiffUtilCallback;
import com.orbitsoft.cryptocurrencyrates.ui.list.PlaceholdersListAdapter;
import com.orbitsoft.cryptocurrencyrates.ui.list.StateAdapter;

import javax.annotation.Nullable;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

@AndroidEntryPoint
public class AppActivity extends AppCompatActivity {

    private AppActivityViewModel viewModel;
    private ActivityAppBinding viewBinding;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Disposable pagingDataDisposable;

    public AppActivity() {
        super(R.layout.activity_app);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding = ActivityAppBinding.inflate(getLayoutInflater());
        setContentView(viewBinding.getRoot());
        viewModel = new ViewModelProvider(this).get(AppActivityViewModel.class);

        initList();
        observePagingData();
        observeOrderState();
        viewBinding.header.setListener(newOrder -> viewModel.onOrderChanged(newOrder));
    }

    private void observeOrderState() {
        compositeDisposable.add(
                viewModel.getOrderSubject()
                        .subscribe(order -> viewBinding.header.bind(order))
        );
    }

    /**
     * Observe paging data subject and subscribe on new flowable
     */
    private void observePagingData() {
        compositeDisposable.add(viewModel.getPagingDataSubject()
                .subscribe(pagingDataFlowable -> {
                            if (pagingDataDisposable != null) pagingDataDisposable.dispose();
                            pagingDataDisposable = pagingDataFlowable
                                    .subscribe(pagingData -> {
                                        CoinrankingListAdapter adapter = selectCoinrankingListAdapter();
                                        if (adapter != null) {
                                            //Caution: The submitData() method suspends and does not return until
                                            //either the PagingSource is invalidated or the adapter's refresh method is called.
                                            //This means that code after the submitData() call might execute much later than you intend.
                                            adapter.submitData(AppActivity.this.getLifecycle(), pagingData);
                                        }
                                    });
                        }
                ));
    }

    private void initList() {
        viewBinding.list.setLayoutManager(new LinearLayoutManager(this));
        viewBinding.list.setHasFixedSize(true);
        Drawable dividerDrawable = ContextCompat.getDrawable(this, R.drawable.row_divider);

        if (dividerDrawable != null) {
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
            dividerItemDecoration.setDrawable(dividerDrawable);
            viewBinding.list.addItemDecoration(dividerItemDecoration);
        }

        CoinrankingListAdapter adapter = new CoinrankingListAdapter(new ListItemDiffUtilCallback(),
                v -> reload());
        PlaceholdersListAdapter placeholdersAdapter = new PlaceholdersListAdapter();
        StateAdapter stateAdapter = new StateAdapter(v -> adapter.retry());
        viewBinding.list.setAdapter(new ConcatAdapter(placeholdersAdapter, adapter, stateAdapter));

        //Add listener to catch empty state exception and show empty state row
        adapter.addLoadStateListener(loadStates -> {
            //Work only with append and refresh because we don't have prepend
            LoadState append = loadStates.getAppend();
            LoadState refresh = loadStates.getRefresh();

            boolean isAppendLoadingOrError = append instanceof LoadState.Loading || append instanceof LoadState.Error;
            boolean isInitialLoading = (append instanceof LoadState.Loading || refresh instanceof LoadState.Loading) &&
                    adapter.getItemCount() == 0;

            //If not init loading show loading state on footer
            if (!isInitialLoading && isAppendLoadingOrError) {
                stateAdapter.setLoadState(append);
            } else {
                //disable loading state on footer
                stateAdapter.setLoadState(new LoadState.NotLoading(true));
            }

            //If init loading then enable placeholders
            placeholdersAdapter.setEnabled(isInitialLoading);


            boolean isNeedShowEmptyState =
                    (append instanceof LoadState.NotLoading && append.getEndOfPaginationReached()) ||
                            (refresh instanceof LoadState.Error ||
                                    (refresh instanceof LoadState.NotLoading && refresh.getEndOfPaginationReached())) &&
                                    adapter.getItemCount() == 0;

            //Show empty state row
            if (isNeedShowEmptyState) {
                adapter.submitData(getLifecycle(), viewModel.getEmptyStatePagingData());
            }

            return null;
        });
    }

    /**
     * Try reload list
     */
    private void reload() {
        viewModel.reload();
    }

    @Nullable
    @SuppressWarnings("rawtypes")
    private CoinrankingListAdapter selectCoinrankingListAdapter() {
        if (viewBinding.list.getAdapter() instanceof ConcatAdapter) {
            for (Adapter adapter : ((ConcatAdapter) viewBinding.list.getAdapter()).getAdapters()) {
                if (adapter instanceof CoinrankingListAdapter) {
                    return (CoinrankingListAdapter) adapter;
                }
            }
        }
        if (viewBinding.list.getAdapter() instanceof CoinrankingListAdapter)
            return (CoinrankingListAdapter) viewBinding.list.getAdapter();

        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewBinding = null;
        compositeDisposable.dispose();
        if (pagingDataDisposable != null) pagingDataDisposable.dispose();
    }
}