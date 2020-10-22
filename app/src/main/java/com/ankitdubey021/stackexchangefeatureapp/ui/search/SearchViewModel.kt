package com.ankitdubey021.stackexchangefeatureapp.ui.search

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.ankitdubey021.stackexchangefeatureapp.data.State
import com.ankitdubey021.stackexchangefeatureapp.data.UserRepository
import com.ankitdubey021.stackexchangefeatureapp.networking.apiUsers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import okhttp3.ResponseBody

class SearchViewModel @ViewModelInject constructor(
        private val userRepository: UserRepository
) : ViewModel(){

    private val _usersLiveData = MutableLiveData<State<ResponseBody>>()
    val usersLiveData = _usersLiveData

    init {
        loadUsers("",1)
    }

    fun loadUsers(name : String,page : Int){
        val url = "$apiUsers&inname=$name&page=$page"

        viewModelScope.launch {
            userRepository.fetchUsers(url)
                    .onStart {
                        emit(State.loading())
                    }
                    .collect {
                        _usersLiveData.postValue(it)
                    }
        }

    }
}