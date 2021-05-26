package com.orbitsoft.cryptocurrencyrates.data.remote.model;

import com.squareup.moshi.Json;

import java.util.List;
import java.util.Objects;

import javax.annotation.Nullable;

public class Coin {
    /**
     * UUID of the coin
     */
    @Json(name = "uuid")
    String uuid;

    /**
     * Currency symbol
     */
    @Json(name = "symbol")
    String symbol;

    /**
     * Name of the coin
     */
    @Json(name = "name")
    String name;

    /**
     * Main HEX color of the coin
     */
    @Json(name = "color")
    String color;

    /**
     * Location of the icon
     */
    @Json(name = "iconUrl")
    String iconUrl;

    /**
     * Market capitalization. Price times circulating supply
     */
    @Nullable
    @Json(name = "marketCap")
    String marketCap;

    /**
     * Price of the coin
     */
    @Json(name = "price")
    String price;

    /**
     * Price of the coin expressed in Bitcoin
     */
    @Json(name = "btcPrice")
    String btcPrice;

    /**
     * Epoch timestamp of when we started listing the coin.
     */
    @Json(name = "listedAt")
    Integer listedAt;

    /**
     * Percentage of change over the given time period
     */
    @Json(name = "change")
    String change;

    /**
     * The position in the ranks
     */
    @Json(name = "rank")
    int rank;

    /**
     * Array of prices based on the time period parameter, usefull for a sparkline
     */
    @Json(name = "sparkline")
    List<Float> sparkline;

    /**
     * Where to find the coin on coinranking.com
     */
    @Json(name = "coinrankingUrl")
    String coinrankingUrl;

    /**
     * 24h trade volume
     */
    @Json(name = "24hVolume")
    String volume24h;

    public String getUuid() {
        return uuid;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    @Nullable
    public String getMarketCap() {
        return marketCap;
    }

    public String getPrice() {
        return price;
    }

    public String getBtcPrice() {
        return btcPrice;
    }

    @Nullable
    public Integer getListedAt() {
        return listedAt;
    }

    public String getChange() {
        return change;
    }

    public int getRank() {
        return rank;
    }

    public List<Float> getSparkline() {
        return sparkline;
    }

    public String getCoinrankingUrl() {
        return coinrankingUrl;
    }

    public String getVolume24h() {
        return volume24h;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coin coin = (Coin) o;
        return rank == coin.rank &&
                Objects.equals(uuid, coin.uuid) &&
                Objects.equals(symbol, coin.symbol) &&
                Objects.equals(name, coin.name) &&
                Objects.equals(color, coin.color) &&
                Objects.equals(iconUrl, coin.iconUrl) &&
                Objects.equals(marketCap, coin.marketCap) &&
                Objects.equals(price, coin.price) &&
                Objects.equals(btcPrice, coin.btcPrice) &&
                Objects.equals(listedAt, coin.listedAt) &&
                Objects.equals(change, coin.change) &&
                Objects.equals(sparkline, coin.sparkline) &&
                Objects.equals(coinrankingUrl, coin.coinrankingUrl) &&
                Objects.equals(volume24h, coin.volume24h);
    }

}
