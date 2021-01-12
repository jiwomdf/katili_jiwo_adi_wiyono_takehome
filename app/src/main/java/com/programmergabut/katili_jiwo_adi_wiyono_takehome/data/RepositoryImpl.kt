package com.programmergabut.katili_jiwo_adi_wiyono_takehome.data

import com.programmergabut.katili_jiwo_adi_wiyono_takehome.base.BaseRepository
import com.programmergabut.katili_jiwo_adi_wiyono_takehome.base.BaseResponse
import com.programmergabut.katili_jiwo_adi_wiyono_takehome.data.remote.api.GithubUsersService
import com.programmergabut.katili_jiwo_adi_wiyono_takehome.data.remote.remoteentity.users.UsersResponse
import kotlinx.coroutines.*
import java.lang.Exception
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val githubUsersService: GithubUsersService
): BaseRepository(), Repository {

    override suspend fun fetchUsers(query: String, page: String, per_page: String): Deferred<UsersResponse> {
        return CoroutineScope(Dispatchers.IO).async {
            lateinit var response: UsersResponse
            try {

                val call = execute(githubUsersService.fetchGitHubUsers(query, page, per_page))

                when {
                    call.isSuccessful -> {
                        response = call.body()!!
                        response.statusResponse = "1"
                        response.messageResponse = call.message()
                    }
                    call.code() == 403 -> {
                        response = UsersResponse()
                        response.statusResponse = "2"
                        response.messageResponse = call.message()
                    }
                    else -> {
                        response = UsersResponse()
                        response.statusResponse = "-1"
                        response.messageResponse = call.message()
                    }
                }

            }
            catch (ex: Exception){
                response = UsersResponse()
                response.statusResponse = "-1"
                response.messageResponse = ex.message.toString()
            }
            response
        }
    }


}