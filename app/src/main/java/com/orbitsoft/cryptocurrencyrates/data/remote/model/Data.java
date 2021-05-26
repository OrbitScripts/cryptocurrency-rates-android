package com.orbitsoft.cryptocurrencyrates.data.remote.model;

import com.squareup.moshi.Json;

import java.util.List;

public class Data {
    /**
     * A series of statistics regarding the requested list.
     * Note that the stats its scope includes coins outside the limit.
     * E.g. the response of a query with a limit of 50 coins returns 50 coins (obviously),
     * while the stats depicts the amount of coins available, 24 hour volume,
     * etc. without this limit, which may be a much higher number
     */
    @Json(name = "stats")
    Stats stats;
    /**
     * List of coins
     */
    @Json(name = "coins")
    List<Coin> coins;

    public Stats getStats() {
        return stats;
    }

    public List<Coin> getCoins() {
        return coins;
    }
}
