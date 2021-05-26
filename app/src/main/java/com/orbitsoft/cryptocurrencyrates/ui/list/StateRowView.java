package com.orbitsoft.cryptocurrencyrates.ui.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.paging.LoadState;

import com.orbitsoft.cryptocurrencyrates.databinding.StateRowViewBinding;

import org.jetbrains.annotations.NotNull;

public class StateRowView extends FrameLayout {
    private StateRowViewBinding viewBinding;

    public StateRowView(@NonNull Context context) {
        super(context);
        initView(context);
    }

    private void initView(@NotNull Context context) {
        viewBinding = StateRowViewBinding.inflate(LayoutInflater.from(context),
                this, true);
        setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    public void setOnRetryBtnClickCallback(OnClickListener onClickListener) {
        viewBinding.retryBtn.setOnClickListener(onClickListener);
    }

    public void bind(@NotNull LoadState loadState) {
        if (loadState instanceof LoadState.Loading) {
            viewBinding.errorWrapper.setVisibility(View.GONE);
            viewBinding.loadingWrapper.setVisibility(View.VISIBLE);
        } else if (loadState instanceof LoadState.Error) {
            viewBinding.loadingWrapper.setVisibility(View.GONE);
            viewBinding.errorWrapper.setVisibility(View.VISIBLE);
        } else {
            viewBinding.loadingWrapper.setVisibility(View.GONE);
            viewBinding.errorWrapper.setVisibility(View.GONE);
        }
    }
}
