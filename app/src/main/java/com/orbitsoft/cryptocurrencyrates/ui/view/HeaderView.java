package com.orbitsoft.cryptocurrencyrates.ui.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;
import com.orbitsoft.cryptocurrencyrates.R;
import com.orbitsoft.cryptocurrencyrates.data.Order;
import com.orbitsoft.cryptocurrencyrates.data.remote.CoinrankingAPI;
import com.orbitsoft.cryptocurrencyrates.databinding.HeaderViewBinding;

import org.jetbrains.annotations.NotNull;

public class HeaderView extends FrameLayout {

    private OnOrderChangedListener listener;
    private HeaderViewBinding viewBinding;
    private Order order;

    public void setListener(OnOrderChangedListener listener) {
        this.listener = listener;
    }


    public HeaderView(@NonNull @NotNull Context context) {
        this(context, null);
    }

    public HeaderView(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeaderView(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public HeaderView(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    private void initView(@NotNull Context context) {
        viewBinding = HeaderViewBinding.inflate(LayoutInflater.from(context), this, true);
        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        viewBinding.headerPrice.setOnClickListener(v -> {
            String newOrderDirection = CoinrankingAPI.ORDER_DIRECTION_DESC;
            if (order.getOrder().equals(CoinrankingAPI.ORDER_MARKET_CAP))
                newOrderDirection = order.getOrderDirection()
                        .equals(CoinrankingAPI.ORDER_DIRECTION_DESC) ?
                        CoinrankingAPI.ORDER_DIRECTION_ASC : CoinrankingAPI.ORDER_DIRECTION_DESC;
            listener.onOrderChanged(new Order(CoinrankingAPI.ORDER_MARKET_CAP, newOrderDirection));
        });

        viewBinding.header24h.setOnClickListener(v -> {
            String newOrderDirection = CoinrankingAPI.ORDER_DIRECTION_DESC;
            if (order.getOrder().equals(CoinrankingAPI.ORDER_CHANGE))
                newOrderDirection = order.getOrderDirection()
                        .equals(CoinrankingAPI.ORDER_DIRECTION_DESC) ?
                        CoinrankingAPI.ORDER_DIRECTION_ASC : CoinrankingAPI.ORDER_DIRECTION_DESC;
            listener.onOrderChanged(new Order(CoinrankingAPI.ORDER_CHANGE, newOrderDirection));
        });
    }

    public void bind(@NotNull Order order) {
        if (this.order != null && this.order.equals(order)) return;
        this.order = order;
        hideOrder();
        switch (order.getOrder()) {
            case CoinrankingAPI.ORDER_MARKET_CAP:
                setArrowIconToView(viewBinding.headerPrice, order.getOrderDirection());
                break;
            case CoinrankingAPI.ORDER_CHANGE:
                setArrowIconToView(viewBinding.header24h, order.getOrderDirection());
                break;
        }
    }

    private void setArrowIconToView(@NotNull MaterialButton button, @NotNull String order) {
        int drawableResId = order.equals(CoinrankingAPI.ORDER_DIRECTION_ASC) ?
                R.drawable.ic_arrow_drop_up : R.drawable.ic_arrow_drop_down;
        button.setIcon(ContextCompat.getDrawable(getContext(), drawableResId));
    }

    /**
     * Hide all order arrows
     */
    private void hideOrder() {
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_arrow_empty);
        viewBinding.headerCryptocurrency.setIcon(drawable);
        viewBinding.header24h.setIcon(drawable);
        viewBinding.headerPrice.setIcon(drawable);
    }


    public interface OnOrderChangedListener {
        void onOrderChanged(@NotNull Order newOrder);
    }
}
