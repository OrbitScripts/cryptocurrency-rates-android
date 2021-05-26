package com.orbitsoft.cryptocurrencyrates.data.remote.model;

import com.squareup.moshi.Json;

public class CoinsResponse {
    /**
     * Status of the request
     */
    @Json(name = "status")
    String status;

    @Json(name = "data")
    Data data;

    public String getStatus() {
        return status;
    }

    public Data getData() {
        return data;
    }
}
