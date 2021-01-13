package com.programmergabut.katili_jiwo_adi_wiyono_takehome.view

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import com.programmergabut.katili_jiwo_adi_wiyono_takehome.base.BaseViewModel
import com.programmergabut.katili_jiwo_adi_wiyono_takehome.data.Repository
import com.programmergabut.katili_jiwo_adi_wiyono_takehome.data.remote.remoteentity.users.Item
import kotlinx.coroutines.launch

class UserViewModel @ViewModelInject constructor(
    private val repository: Repository
): BaseViewModel() {

    companion object {
        const val DEFAULT_QUERY = "jiwo"
    }

    private val currentQuery = MutableLiveData(DEFAULT_QUERY)
    val userss = currentQuery.switchMap { queryString ->
        repository.getSearchResult(queryString).cachedIn(viewModelScope)
    }
    fun searchPhoto(query: String){
        currentQuery.value = query
    }


    /* private var message: String = ""
    fun getMessage() = message

    private var users: List<Item?> = listOf()
    fun getUsers() = users

    private var _usersStatus: MutableLiveData<Int> = MutableLiveData()
    val usersStatus = _usersStatus

    fun fetchListUser(query: String, page: Int, per_page: Int) {
        viewModelScope.launch {
            setSearchLoader(true, page)
            val response = repository.fetchUsers(query, page.toString(), per_page.toString()).await()

            when(response.statusResponse.toInt()){
                1 -> {
                    if(response != null && !response.items.isNullOrEmpty()){
                        users = response.items
                        message = response.messageResponse
                        _usersStatus.postValue(SUCCESS)
                    }
                    else{
                        _usersStatus.postValue(ERROR)
                        //message = response.message
                    }
                }
                -1 -> {
                    _usersStatus.postValue(ERROR)
                    message = response.messageResponse
                }
                2 -> {
                    _usersStatus.postValue(LIMIT)
                    message = response.messageResponse
                }
                else -> {
                    _usersStatus.postValue(ERROR)
                    message = response.messageResponse
                }
            }

            setSearchLoader(false, page)
        }
    }

    private fun setSearchLoader(isLoading: Boolean, page: Int){
        if(page > 1)
            return

        if(isLoading)
            loading.postValue(SHOW_LOADING)
        else
            loading.postValue(REMOVE_LOADING)
    } */


}