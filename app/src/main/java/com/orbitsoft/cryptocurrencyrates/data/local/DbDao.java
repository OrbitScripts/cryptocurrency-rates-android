package com.orbitsoft.cryptocurrencyrates.data.local;

import androidx.paging.PagingSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.orbitsoft.cryptocurrencyrates.data.local.model.CoinEntity;
import com.orbitsoft.cryptocurrencyrates.data.local.model.RemoteOffsetEntity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

import static com.orbitsoft.cryptocurrencyrates.data.local.model.CoinEntity.COINS_TABLE;
import static com.orbitsoft.cryptocurrencyrates.data.local.model.RemoteOffsetEntity.REMOTE_OFFSET_TABLE;

@Dao
public interface DbDao {

    String WHERE = " WHERE `order` = :order AND orderDirection = :orderDirection";

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCoins(@NotNull List<CoinEntity> coins);

    @Query("SELECT * FROM " + COINS_TABLE + WHERE)
    PagingSource<Integer, CoinEntity> pagingSourceByOrder(@NotNull String order, @NotNull String orderDirection);

    @Query("SELECT addedAt FROM " + COINS_TABLE + WHERE + " ORDER BY addedAt LIMIT 1")
    Single<Long> getAddedAtOrder(@NotNull String order, @NotNull String orderDirection);

    @Query("DELETE FROM " + COINS_TABLE + WHERE)
    int clearCacheForOrder(@NotNull String order, @NotNull String orderDirection);

    @Query("DELETE FROM " + COINS_TABLE)
    int clearAllCache();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOrReplaceRemoteOffset(@NotNull RemoteOffsetEntity remoteOffset);

    @Query("SELECT * FROM " + REMOTE_OFFSET_TABLE + WHERE)
    Single<RemoteOffsetEntity> remoteOffsetByOrder(@NotNull String order, @NotNull String orderDirection);

    @Query("DELETE FROM " + REMOTE_OFFSET_TABLE + WHERE)
    void deleteRemoteOffsetByOrder(@NotNull String order, @NotNull String orderDirection);

    @Query("DELETE FROM " + REMOTE_OFFSET_TABLE)
    int clearRemoteOffset();

}
