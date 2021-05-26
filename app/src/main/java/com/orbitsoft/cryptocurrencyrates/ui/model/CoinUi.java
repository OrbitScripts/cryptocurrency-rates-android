package com.orbitsoft.cryptocurrencyrates.ui.model;

import com.orbitsoft.cryptocurrencyrates.data.local.model.CoinEntity;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

import javax.annotation.Nullable;

public class CoinUi {
    /**
     * UUID of the coin
     */
    private final String localUuid;

    /**
     * Currency symbol
     */
    private final String symbol;

    /**
     * Name of the coin
     */
    private final String name;

    /**
     * Main HEX color of the coin
     */
    private final String color;

    /**
     * Location of the icon
     */
    private final String iconUrl;

    /**
     * Market capitalization. Price times circulating supply
     */
    @Nullable
    private final String marketCap;

    /**
     * Price of the coin
     */
    private final String price;

    /**
     * Price of the coin expressed in Bitcoin
     */
    private final String btcPrice;

    /**
     * Epoch timestamp of when we started listing the coin.
     */
    private final Integer listedAt;

    /**
     * Percentage of change over the given time period
     */
    private final String change;

    /**
     * The position in the ranks
     */
    private final int rank;

    /**
     * Array of prices based on the time period parameter, usefull for a sparkline
     */
    private final List<Float> sparkline;

    /**
     * Where to find the coin on coinranking.com
     */
    private final String coinrankingUrl;

    /**
     * 24h trade volume
     */
    private final String volume24h;

    /**
     * Position
     */
    private final long posititon;

    public String getLocalUuid() {
        return localUuid;
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

    public long getPosititon() {
        return posititon;
    }

    public CoinUi(String localUuid, String symbol, String name, String color, String iconUrl, @Nullable String marketCap, String price, String btcPrice, Integer listedAt, String change, int rank, List<Float> sparkline, String coinrankingUrl, String volume24h, long posititon) {
        this.localUuid = localUuid;
        this.symbol = symbol;
        this.name = name;
        this.color = color;
        this.iconUrl = iconUrl;
        this.marketCap = marketCap;
        this.price = price;
        this.btcPrice = btcPrice;
        this.listedAt = listedAt;
        this.change = change;
        this.rank = rank;
        this.sparkline = sparkline;
        this.coinrankingUrl = coinrankingUrl;
        this.volume24h = volume24h;
        this.posititon = posititon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoinUi coinUi = (CoinUi) o;
        return rank == coinUi.rank &&
                Objects.equals(localUuid, coinUi.localUuid) &&
                Objects.equals(symbol, coinUi.symbol) &&
                Objects.equals(name, coinUi.name) &&
                Objects.equals(color, coinUi.color) &&
                Objects.equals(iconUrl, coinUi.iconUrl) &&
                Objects.equals(marketCap, coinUi.marketCap) &&
                Objects.equals(price, coinUi.price) &&
                Objects.equals(btcPrice, coinUi.btcPrice) &&
                Objects.equals(listedAt, coinUi.listedAt) &&
                Objects.equals(change, coinUi.change) &&
                Objects.equals(sparkline, coinUi.sparkline) &&
                Objects.equals(coinrankingUrl, coinUi.coinrankingUrl) &&
                Objects.equals(volume24h, coinUi.volume24h);
    }

    @Override
    public int hashCode() {
        return Objects.hash(localUuid, symbol, name, color, iconUrl, marketCap, price, btcPrice, listedAt, change, rank, sparkline, coinrankingUrl, volume24h);
    }

    public static CoinUi fromEntity(@NotNull CoinEntity entity) {
        return new CoinUi(
                entity.getLocalUuid(),
                entity.getSymbol(),
                entity.getName(),
                entity.getColor(),
                entity.getIconUrl(),
                entity.getMarketCap(),
                entity.getPrice(),
                entity.getBtcPrice(),
                entity.getListedAt(),
                entity.getChange(),
                entity.getRank(),
                entity.getSparkline(),
                entity.getCoinrankingUrl(),
                entity.getVolume24h(),
                entity.getPosition()
        );
    }

    @Override
    public String toString() {
        return "CoinUi{" +
                "localUuid='" + localUuid + '\'' +
                ", symbol='" + symbol + '\'' +
                ", name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", iconUrl='" + iconUrl + '\'' +
                ", marketCap='" + marketCap + '\'' +
                ", price='" + price + '\'' +
                ", btcPrice='" + btcPrice + '\'' +
                ", listedAt=" + listedAt +
                ", change='" + change + '\'' +
                ", rank=" + rank +
                ", sparkline=" + sparkline +
                ", coinrankingUrl='" + coinrankingUrl + '\'' +
                ", volume24h='" + volume24h + '\'' +
                ", posititon=" + posititon +
                '}';
    }
}
