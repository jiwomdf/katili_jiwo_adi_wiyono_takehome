package com.programmergabut.katili_jiwo_adi_wiyono_takehome.data

/*
   Created by Katili Jiwo A.W. 12 January 2021
*/

import com.programmergabut.katili_jiwo_adi_wiyono_takehome.data.remote.remoteentity.users.UsersResponse
import kotlinx.coroutines.Deferred

interface Repository {
    suspend fun fetchUsersAsync(query: String, page: String, per_page: String): Deferred<UsersResponse>
}