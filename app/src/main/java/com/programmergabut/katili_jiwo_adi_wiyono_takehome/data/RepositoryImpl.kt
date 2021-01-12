package com.programmergabut.katili_jiwo_adi_wiyono_takehome.data

import com.programmergabut.katili_jiwo_adi_wiyono_takehome.base.BaseRepository
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
                response = execute(githubUsersService.fetchGitHubUsers(
                    query, page, per_page
                ))
                response.status = "1"
                response.message = "Success"
            }
            catch (ex: Exception){
                response = UsersResponse()
                response.status = "-1"
                response.message = ex.message.toString()
            }
            response
        }
    }


}