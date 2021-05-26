package com.orbitsoft.cryptocurrencyrates.ui.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.orbitsoft.cryptocurrencyrates.databinding.PlaceholderRowViewBinding;

import org.jetbrains.annotations.NotNull;

public class PlaceholderRowView extends FrameLayout {
    private PlaceholderRowViewBinding viewBinding;

    public PlaceholderRowView(@NonNull Context context) {
        super(context);
        initView(context);
    }

    private void initView(@NotNull Context context) {
        this.viewBinding = PlaceholderRowViewBinding.inflate(LayoutInflater.from(context), this, true);
        setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    public void bind() {
        viewBinding.shimmerLayout.startShimmer();
    }

    public void recycle() {
        viewBinding.shimmerLayout.stopShimmer();
    }
}
