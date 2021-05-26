package com.orbitsoft.cryptocurrencyrates.data.remote;

import com.orbitsoft.cryptocurrencyrates.data.Order;
import com.orbitsoft.cryptocurrencyrates.data.remote.model.CoinsResponse;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.rxjava3.core.Single;

@Singleton
public class CoinrankingSource {

    private final CoinrankingAPI api;

    @Inject
    public CoinrankingSource(@NotNull CoinrankingAPI api) {
        this.api = api;
    }

    public Single<CoinsResponse> loadCoins(int offset, int limit, @NotNull Order order) {
        return api.loadCoins(offset, limit, order.getOrder(), order.getOrderDirection());
    }
}
