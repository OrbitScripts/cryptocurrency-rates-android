package com.orbitsoft.cryptocurrencyrates.data.remote;

import com.orbitsoft.cryptocurrencyrates.data.remote.model.CoinsResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CoinrankingAPI {

    String ORDER_MARKET_CAP = "marketCap";
    String ORDER_CHANGE = "change";
    String ORDER_DIRECTION_ASC = "asc";
    String ORDER_DIRECTION_DESC = "desc";


    @GET("coins")
    Single<CoinsResponse> loadCoins(@Query("offset") int offset,
                                    @Query("limit") int limit,
                                    @Query("orderBy") String order,
                                    @Query("orderDirection") String orderDirection);


}
