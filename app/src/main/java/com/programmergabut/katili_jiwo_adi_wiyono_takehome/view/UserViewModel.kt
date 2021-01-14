package com.programmergabut.katili_jiwo_adi_wiyono_takehome.view

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.*
import com.programmergabut.katili_jiwo_adi_wiyono_takehome.AbsentLiveData
import com.programmergabut.katili_jiwo_adi_wiyono_takehome.Constant
import com.programmergabut.katili_jiwo_adi_wiyono_takehome.base.BaseViewModel
import com.programmergabut.katili_jiwo_adi_wiyono_takehome.data.Repository
import com.programmergabut.katili_jiwo_adi_wiyono_takehome.data.remote.UserPagingSource
import com.programmergabut.katili_jiwo_adi_wiyono_takehome.data.remote.remoteentity.users.Item

class UserViewModel @ViewModelInject constructor(
    private val repository: Repository
): BaseViewModel() {

    private val currQuery = MutableLiveData<String>()
    val users = Transformations.switchMap(currQuery) { queryString ->
        if(queryString.isNullOrEmpty()){
            AbsentLiveData.create()
        } else {
            getSearchResult(queryString).cachedIn(viewModelScope)
        }
    }
    fun searchPhoto(query: String){
        currQuery.value = query
    }

    private fun getSearchResult(query: String): LiveData<PagingData<Item>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constant.GITHUB_API_PER_PAGE,
                maxSize = Constant.PAGER_MAX_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { UserPagingSource(repository, query) }
        ).liveData
    }


}