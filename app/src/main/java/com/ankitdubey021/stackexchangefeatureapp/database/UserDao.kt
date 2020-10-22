package com.ankitdubey021.stackexchangefeatureapp.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {
    @Query("select * from userdb")
    fun getFavUsers(): LiveData<List<UserDB>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll( user: List<UserDB>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save( user: UserDB)

    @Delete
    fun delete( user: UserDB)

    @Query("SELECT * FROM userdb WHERE userId = :userId")
    fun find(userId: Int): List<UserDB>

}