package com.programmergabut.katili_jiwo_adi_wiyono_takehome.data.remote.api

import com.programmergabut.katili_jiwo_adi_wiyono_takehome.data.remote.remoteentity.users.UsersResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface GithubUsersService {
    @GET("search/users")
    fun fetchGitHubUsers(
        @Query("q") query: String
    ): Call<UsersResponse>
}