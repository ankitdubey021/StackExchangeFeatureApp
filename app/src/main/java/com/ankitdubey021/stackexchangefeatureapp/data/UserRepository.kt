package com.ankitdubey021.stackexchangefeatureapp.data
import com.ankitdubey021.stackexchangefeatureapp.networking.ApiService
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
        private val apiService: ApiService
) {

    fun fetchUsers(url : String) =

        flow<State<ResponseBody>>{
            val response = apiService.doGetApiCall(url)

            if(response.isSuccessful)
                emit(State.success(response.body()!!))
            else
                emit(State.error(response.errorBody()!!))
        }

    }
