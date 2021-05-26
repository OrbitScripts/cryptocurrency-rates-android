package com.orbitsoft.cryptocurrencyrates.data.local.model;

import androidx.room.Entity;

import com.orbitsoft.cryptocurrencyrates.data.Order;
import com.orbitsoft.cryptocurrencyrates.data.remote.model.Coin;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import static com.orbitsoft.cryptocurrencyrates.data.local.model.CoinEntity.COINS_TABLE;

@Entity(tableName = COINS_TABLE,
        primaryKeys = {"uuid", "localUuid", "order", "orderDirection", "position"})
public class CoinEntity {

    public static final String COINS_TABLE = "coins";

    /**
     * UUID of the coin
     */
    @NotNull
    private String uuid;

    /**
     * UUID of the coin inside local db (generated from uuid and order and addedAt)
     */
    @NotNull
    private String localUuid;

    /**
     * Order
     */
    @NotNull
    private String order;

    /**
     * Order direction
     */
    @NotNull
    private String orderDirection;

    /**
     * When record was saved
     */
    private long addedAt;

    /**
     * Position in request
     */
    private long position;

    /**
     * Currency symbol
     */
    private String symbol;

    /**
     * Name of the coin
     */
    private String name;

    /**
     * Main HEX color of the coin
     */
    private String color;

    /**
     * Location of the icon
     */
    private String iconUrl;

    /**
     * Market capitalization. Price times circulating supply
     */
    @Nullable
    private String marketCap;

    /**
     * Price of the coin
     */
    private String price;

    /**
     * Price of the coin expressed in Bitcoin
     */
    private String btcPrice;

    /**
     * Epoch timestamp of when we started listing the coin.
     */
    private Integer listedAt;

    /**
     * Percentage of change over the given time period
     */
    private String change;

    /**
     * The position in the ranks
     */
    private int rank;

    /**
     * Array of prices based on the time period parameter, usefull for a sparkline
     */
    private List<Float> sparkline;

    /**
     * Where to find the coin on coinranking.com
     */
    private String coinrankingUrl;

    /**
     * 24h trade volume
     */
    private String volume24h;


    @NotNull
    public String getUuid() {
        return uuid;
    }

    public void setUuid(@NotNull String uuid) {
        this.uuid = uuid;
    }

    @NotNull
    public String getLocalUuid() {
        return localUuid;
    }

    public void setLocalUuid(@NotNull String localUuid) {
        this.localUuid = localUuid;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    @Nullable
    public String getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(@Nullable String marketCap) {
        this.marketCap = marketCap;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBtcPrice() {
        return btcPrice;
    }

    public void setBtcPrice(String btcPrice) {
        this.btcPrice = btcPrice;
    }

    public Integer getListedAt() {
        return listedAt;
    }

    public void setListedAt(Integer listedAt) {
        this.listedAt = listedAt;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public List<Float> getSparkline() {
        return sparkline;
    }

    public void setSparkline(List<Float> sparkline) {
        this.sparkline = sparkline;
    }

    public String getCoinrankingUrl() {
        return coinrankingUrl;
    }

    public void setCoinrankingUrl(String coinrankingUrl) {
        this.coinrankingUrl = coinrankingUrl;
    }

    public String getVolume24h() {
        return volume24h;
    }

    public void setVolume24h(String volume24h) {
        this.volume24h = volume24h;
    }

    @NotNull
    public String getOrder() {
        return order;
    }

    public void setOrder(@NotNull String order) {
        this.order = order;
    }

    @NotNull
    public String getOrderDirection() {
        return orderDirection;
    }

    public void setOrderDirection(@NotNull String orderDirection) {
        this.orderDirection = orderDirection;
    }

    public long getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(long addedAt) {
        this.addedAt = addedAt;
    }

    public long getPosition() {
        return position;
    }

    public void setPosition(long position) {
        this.position = position;
    }

    public CoinEntity(@NotNull String uuid, @NotNull String localUuid, String symbol, String name, String color, String iconUrl, @Nullable String marketCap, String price, String btcPrice, Integer listedAt, String change, int rank, List<Float> sparkline, String coinrankingUrl, String volume24h, @NotNull String order, @NotNull String orderDirection, long addedAt, long position) {
        this.uuid = uuid;
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
        this.order = order;
        this.orderDirection = orderDirection;
        this.addedAt = addedAt;
        this.position = position;
    }

    public static List<CoinEntity> fromRemoteList(@NotNull List<Coin> list, @NotNull Order order, long startPagePosition) {
        long time = System.currentTimeMillis();
        ArrayList<CoinEntity> entites = new ArrayList<>();
        for (Coin coin : list) {
            entites.add(
                    new CoinEntity(
                            coin.getUuid(),
                            UUID.nameUUIDFromBytes((coin.getUuid() + order.getOrder() + order.getOrderDirection()).getBytes()).toString(),//generate local uuid
                            coin.getSymbol(),
                            coin.getName(),
                            coin.getColor(),
                            coin.getIconUrl(),
                            coin.getMarketCap(),
                            coin.getPrice(),
                            coin.getBtcPrice(),
                            coin.getListedAt(),
                            coin.getChange(),
                            coin.getRank(),
                            coin.getSparkline(),
                            coin.getCoinrankingUrl(),
                            coin.getVolume24h(),
                            order.getOrder(),
                            order.getOrderDirection(),
                            time,
                            ++startPagePosition
                    )
            );
        }
        return entites;
    }
}
