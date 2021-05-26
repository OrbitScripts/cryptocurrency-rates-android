package com.orbitsoft.cryptocurrencyrates.ui.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.orbitsoft.cryptocurrencyrates.databinding.EmptyStateRowViewBinding;

import org.jetbrains.annotations.NotNull;

public class EmptyStateRowView extends FrameLayout {
    private final OnClickListener onRetryClickListener;
    private EmptyStateRowViewBinding viewBinding;

    public EmptyStateRowView(@NonNull Context context, @NotNull OnClickListener onRetryClickListener) {
        super(context);
        this.onRetryClickListener = onRetryClickListener;
        initView(context);
    }

    private void initView(@NotNull Context context) {
        this.viewBinding = EmptyStateRowViewBinding.inflate(LayoutInflater.from(context), this, true);
        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        viewBinding.retryBtn.setOnClickListener(onRetryClickListener);
    }

}
