package com.programmergabut.katili_jiwo_adi_wiyono_takehome.view

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.programmergabut.katili_jiwo_adi_wiyono_takehome.base.BaseViewModel
import com.programmergabut.katili_jiwo_adi_wiyono_takehome.data.Repository
import com.programmergabut.katili_jiwo_adi_wiyono_takehome.data.remote.remoteentity.users.Item
import kotlinx.coroutines.launch

class UserViewModel @ViewModelInject constructor(
    val repository: Repository
): BaseViewModel() {

    private var message: String = ""
    fun getMessage() = message

    private var users: List<Item> = listOf()
    fun getUsers() = users

    private var _usersStatus: MutableLiveData<Int> = MutableLiveData()
    val usersStatus = _usersStatus

    fun fetchListUser(query: String) {
        viewModelScope.launch {
            loading.postValue(SHOW_LOADING)
            val response = repository.fetchUsers(query).await()

            when(response.status.toInt()){
                1 -> {
                    users = response.items
                    message = response.message
                    _usersStatus.postValue(SUCCESS)
                }
                -1 -> {
                    _usersStatus.postValue(ERROR)
                    message = response.message
                }
                else -> {

                }
            }

            loading.postValue(REMOVE_LOADING)
        }
    }


}