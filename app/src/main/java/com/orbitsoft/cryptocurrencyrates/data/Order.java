package com.orbitsoft.cryptocurrencyrates.data;

import com.orbitsoft.cryptocurrencyrates.data.remote.CoinrankingAPI;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Order {
    private final String order;
    private final String orderDirection;

    public String getOrder() {
        return order;
    }

    public String getOrderDirection() {
        return orderDirection;
    }

    public Order(@NotNull String order, @NotNull String orderDirection) {
        this.order = order;
        this.orderDirection = orderDirection;
    }

    public static Order buildDefault() {
        return new Order(CoinrankingAPI.ORDER_MARKET_CAP, CoinrankingAPI.ORDER_DIRECTION_DESC);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order1 = (Order) o;
        return Objects.equals(order, order1.order) &&
                Objects.equals(orderDirection, order1.orderDirection);
    }

}
