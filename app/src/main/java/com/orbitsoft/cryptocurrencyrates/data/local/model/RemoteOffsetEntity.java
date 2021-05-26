package com.orbitsoft.cryptocurrencyrates.data.local.model;

import androidx.room.Entity;

import com.orbitsoft.cryptocurrencyrates.data.Order;

import org.jetbrains.annotations.NotNull;

import static com.orbitsoft.cryptocurrencyrates.data.local.model.RemoteOffsetEntity.REMOTE_OFFSET_TABLE;

@Entity(tableName = REMOTE_OFFSET_TABLE,
        primaryKeys = {"order", "orderDirection"})
public class RemoteOffsetEntity {

    public static final String REMOTE_OFFSET_TABLE = "remote_offset";

    /**
     * Next offset for fetch from remote source
     */
    @NotNull
    private Integer nextOffset;

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


    @NotNull
    public Integer getNextOffset() {
        return nextOffset;
    }

    public void setNextOffset(@NotNull Integer nextOffset) {
        this.nextOffset = nextOffset;
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

    public RemoteOffsetEntity(@NotNull Integer nextOffset, @NotNull String order, @NotNull String orderDirection) {
        this.nextOffset = nextOffset;
        this.order = order;
        this.orderDirection = orderDirection;
    }

    public RemoteOffsetEntity(@NotNull Integer nextOffset, Order order) {
        this.nextOffset = nextOffset;
        this.order = order.getOrder();
        this.orderDirection = order.getOrderDirection();
    }
}
