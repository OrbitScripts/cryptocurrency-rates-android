package com.orbitsoft.cryptocurrencyrates.di.modules;

import android.content.Context;

import androidx.room.Room;

import com.orbitsoft.cryptocurrencyrates.data.db.AppDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class DatabaseModule {
    @Singleton
    @Provides
    AppDatabase provideDB(@ApplicationContext Context appContext) {
        return Room.databaseBuilder(appContext, AppDatabase.class, "coins_db").build();
    }
}
