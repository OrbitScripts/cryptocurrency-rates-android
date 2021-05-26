package com.orbitsoft.cryptocurrencyrates.data.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.orbitsoft.cryptocurrencyrates.data.local.DbDao;
import com.orbitsoft.cryptocurrencyrates.data.local.model.CoinEntity;
import com.orbitsoft.cryptocurrencyrates.data.local.model.RemoteOffsetEntity;

@Database(entities = {CoinEntity.class, RemoteOffsetEntity.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract DbDao coinsDao();
}
