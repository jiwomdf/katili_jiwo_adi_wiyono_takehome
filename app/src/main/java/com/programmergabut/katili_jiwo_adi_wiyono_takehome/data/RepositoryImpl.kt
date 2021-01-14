package com.programmergabut.katili_jiwo_adi_wiyono_takehome.data

/*
   Created by Katili Jiwo A.W. 12 January 2021
*/

import com.programmergabut.katili_jiwo_adi_wiyono_takehome.base.BaseRepository
import com.programmergabut.katili_jiwo_adi_wiyono_takehome.data.remote.api.GithubUsersService
import com.programmergabut.katili_jiwo_adi_wiyono_takehome.data.remote.remoteentity.users.UsersResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val githubUsersService: GithubUsersService
): BaseRepository(), Repository {

    override suspend fun fetchUsersAsync(query: String, page: String, per_page: String): Deferred<UsersResponse> {
        return CoroutineScope(Dispatchers.IO).async {
            lateinit var response: UsersResponse
            try {
                val result = execute(githubUsersService.fetchGitHubUsers(query, page, per_page))
                response = result
                response.status = "1"
            }
            catch (ex: IOException){
                response = UsersResponse()
                response.status = "-1"
                response.exception = IOException(ex.message.toString())
            }
            catch (ex: UnknownHostException){
                response = UsersResponse()
                response.status = "-1"
                response.exception = UnknownHostException(ex.message.toString())
            }
            catch (ex: HttpException){
                response = UsersResponse()
                response.status = "-1"
                response.exception = HttpException(ex.response()!!)
            }
            catch (ex: Exception){
                response = UsersResponse()
                response.status = "-1"
                response.exception = Exception(ex.message.toString())
            }
            response
        }
    }

}