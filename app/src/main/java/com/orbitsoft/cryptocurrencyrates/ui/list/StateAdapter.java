package com.orbitsoft.cryptocurrencyrates.ui.list;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.LoadState;
import androidx.paging.LoadStateAdapter;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

/**
 * Adapter to show state (loading, error) row in footer
 */
public class StateAdapter extends LoadStateAdapter<StateAdapter.StateVH> {

    private final View.OnClickListener onRetryClickListener;

    public StateAdapter(View.OnClickListener onRetryClickListener) {
        this.onRetryClickListener = onRetryClickListener;
    }

    @Override
    public void onBindViewHolder(@NotNull StateVH stateVH, @NotNull LoadState loadState) {
        stateVH.bind(loadState);
    }

    @NotNull
    @Override
    public StateVH onCreateViewHolder(@NotNull ViewGroup viewGroup, @NotNull LoadState loadState) {
        StateRowView stateRowView = new StateRowView(viewGroup.getContext());
        stateRowView.setOnRetryBtnClickCallback(onRetryClickListener);
        return new StateVH(stateRowView);
    }

    static class StateVH extends RecyclerView.ViewHolder {
        public StateVH(@NonNull @NotNull View itemView) {
            super(itemView);
        }

        public void bind(@NotNull LoadState loadState) {
            if (itemView instanceof StateRowView) {
                ((StateRowView) itemView).bind(loadState);
            }
        }
    }
}
