package com.orbitsoft.cryptocurrencyrates.ui.list;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class CoinrankingListAdapter extends PagingDataAdapter<ListItem, RecyclerView.ViewHolder> {

    private static final int ITEM_TYPE_COIN = 0;
    private static final int ITEM_TYPE_EMPTY_STATE = 1;

    private final View.OnClickListener onRetryClickListener;

    public CoinrankingListAdapter(@NotNull DiffUtil.ItemCallback<ListItem> diffCallback,
                                  @NotNull View.OnClickListener onRetryClickListener) {
        super(diffCallback);
        this.onRetryClickListener = onRetryClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        ListItem item = getItem(position);
        if (item instanceof ListItem.EmptyState) return ITEM_TYPE_EMPTY_STATE;
        if (item == null || item instanceof ListItem.Row) return ITEM_TYPE_COIN;
        throw new IllegalArgumentException("Item types " + item.getClass() +
                " is not allowed. Pos: " + position + "; count: " + getItemCount());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case ITEM_TYPE_COIN:
                return new CoinVH(new CoinRowView(parent.getContext()));
            case ITEM_TYPE_EMPTY_STATE:
                return new EmptyStateVH(new EmptyStateRowView(parent.getContext(), onRetryClickListener));
            default:
                throw new IllegalStateException("ViewType " + viewType + " is not allowed");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        ListItem item = getItem(position);

        if (item instanceof ListItem.Row && holder instanceof CoinVH)
            ((CoinVH) holder).bind((ListItem.Row) item);
    }

    @Override
    public void onViewRecycled(@NonNull @NotNull RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
        if (holder instanceof CoinVH) {
            ((CoinVH) holder).recycle();
        }
    }

    static class CoinVH extends RecyclerView.ViewHolder {

        public CoinVH(@NonNull @NotNull View itemView) {
            super(itemView);
        }

        public void bind(@NotNull ListItem.Row row) {
            if (itemView instanceof CoinRowView) {
                ((CoinRowView) itemView).bind(row.getCoin());
            }
        }

        public void recycle() {
            if (itemView instanceof CoinRowView) {
                ((CoinRowView) itemView).recycle();
            }
        }
    }

    static class EmptyStateVH extends RecyclerView.ViewHolder {

        public EmptyStateVH(@NonNull @NotNull View itemView) {
            super(itemView);
        }
    }
}
