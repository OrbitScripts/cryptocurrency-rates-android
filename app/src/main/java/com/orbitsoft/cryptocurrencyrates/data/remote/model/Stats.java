package com.orbitsoft.cryptocurrencyrates.data.remote.model;

import com.squareup.moshi.Json;

public class Stats {
    /**
     * Total number of coins within the query
     */
    @Json(name = "total")
    int total;
    /**
     * Total number of markets within the query
     */
    @Json(name = "totalMarkets")
    int totalMarkets;
    /**
     * Total number of exchanges within the query
     */
    @Json(name = "totalExchanges")
    int totalExchanges;
    /**
     * The market capital of coins within the query
     */
    @Json(name = "totalMarketCap")
    String totalMarketCap;
    /**
     * The volume over the last 24 hours of coins within the query
     */
    @Json(name = "total24hVolume")
    String total24hVolume;

    public int getTotal() {
        return total;
    }

    public int getTotalMarkets() {
        return totalMarkets;
    }

    public int getTotalExchanges() {
        return totalExchanges;
    }

    public String getTotalMarketCap() {
        return totalMarketCap;
    }

    public String getTotal24hVolume() {
        return total24hVolume;
    }
}
