package com.orbitsoft.cryptocurrencyrates.ui.list;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class PlaceholdersListAdapter extends RecyclerView.Adapter<PlaceholdersListAdapter.PlaceholderVH> {

    private int numPlaceholders = 0;

    public void setEnabled(boolean enabled) {
        if (enabled && numPlaceholders == 0) {
            this.numPlaceholders = 20;
            notifyDataSetChanged();
        }
        if (!enabled && numPlaceholders > 0) {
            this.numPlaceholders =  0;
            notifyDataSetChanged();
        }
    }

    @NotNull
    @Override
    public PlaceholderVH onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new PlaceholdersListAdapter.PlaceholderVH(new PlaceholderRowView(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PlaceholderVH holder, int position) {
        holder.bind();
    }

    @Override
    public int getItemCount() {
        return numPlaceholders;
    }

    @Override
    public void onViewRecycled(@NonNull @NotNull PlaceholderVH holder) {
        super.onViewRecycled(holder);
        holder.recycle();
    }

    static class PlaceholderVH extends RecyclerView.ViewHolder {

        public PlaceholderVH(@NonNull @NotNull View itemView) {
            super(itemView);
        }

        public void bind() {
            if (itemView instanceof PlaceholderRowView) {
                ((PlaceholderRowView) itemView).bind();
            }
        }

        public void recycle() {
            if (itemView instanceof PlaceholderRowView) {
                ((PlaceholderRowView) itemView).recycle();
            }
        }
    }
}
