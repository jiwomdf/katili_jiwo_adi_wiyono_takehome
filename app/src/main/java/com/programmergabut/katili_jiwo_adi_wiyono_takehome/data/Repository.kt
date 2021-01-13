package com.programmergabut.katili_jiwo_adi_wiyono_takehome.data

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.programmergabut.katili_jiwo_adi_wiyono_takehome.data.remote.remoteentity.users.Item
import com.programmergabut.katili_jiwo_adi_wiyono_takehome.data.remote.remoteentity.users.UsersResponse
import kotlinx.coroutines.Deferred

interface Repository {

    //suspend fun fetchUsers(query: String, page: String, per_page: String): Deferred<UsersResponse>
    fun getSearchResult(query: String): LiveData<PagingData<Item>>
}