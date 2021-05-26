package com.orbitsoft.cryptocurrencyrates.ui.list;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

/**
 * @TODO implement getChangePayload to make UI smoother
 */
public class ListItemDiffUtilCallback extends DiffUtil.ItemCallback<ListItem> {
    @Override
    public boolean areItemsTheSame(@NonNull ListItem oldItem, @NonNull ListItem newItem) {
        if (oldItem instanceof ListItem.Row && newItem instanceof ListItem.Row)
            return ((ListItem.Row) oldItem).getCoin().getLocalUuid().equals(((ListItem.Row) newItem).getCoin().getLocalUuid());
        return false;
    }

    @Override
    public boolean areContentsTheSame(@NonNull ListItem oldItem, @NonNull ListItem newItem) {
        if (oldItem instanceof ListItem.Row && newItem instanceof ListItem.Row)
            return ((ListItem.Row) oldItem).getCoin().equals(((ListItem.Row) newItem).getCoin());
        return false;
    }

}
