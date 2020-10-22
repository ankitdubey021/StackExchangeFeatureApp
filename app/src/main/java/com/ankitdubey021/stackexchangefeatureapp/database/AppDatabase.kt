package com.ankitdubey021.stackexchangefeatureapp.database

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [UserDB::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
