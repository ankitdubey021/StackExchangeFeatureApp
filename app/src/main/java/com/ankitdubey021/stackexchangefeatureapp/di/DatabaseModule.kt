package com.ankitdubey021.stackexchangefeatureapp.di

import android.content.Context
import androidx.room.Room
import com.ankitdubey021.stackexchangefeatureapp.data.DATABASE_DB_NAME
import com.ankitdubey021.stackexchangefeatureapp.database.AppDatabase
import com.ankitdubey021.stackexchangefeatureapp.database.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object DatabaseModule{

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            DATABASE_DB_NAME
        ).build()
    }

    @Provides
    fun provideDeveloperDao(database: AppDatabase): UserDao {
        return database.userDao()
    }

}