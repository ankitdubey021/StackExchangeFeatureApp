package com.ankitdubey021.stackexchangefeatureapp.ui.wishlist

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ankitdubey021.stackexchangefeatureapp.database.UserDB
import com.ankitdubey021.stackexchangefeatureapp.database.UserDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WishViewModel @ViewModelInject constructor(
    private val userDao: UserDao
) : ViewModel(){

    val favUsers = userDao.getFavUsers()

    fun removeFromFavorite(user : UserDB){
        viewModelScope.launch(Dispatchers.IO){
            userDao.delete(user)
        }
    }

}