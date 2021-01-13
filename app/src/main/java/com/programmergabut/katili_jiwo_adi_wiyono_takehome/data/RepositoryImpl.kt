package com.programmergabut.katili_jiwo_adi_wiyono_takehome.data

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.programmergabut.katili_jiwo_adi_wiyono_takehome.Constant
import com.programmergabut.katili_jiwo_adi_wiyono_takehome.base.BaseRepository
import com.programmergabut.katili_jiwo_adi_wiyono_takehome.data.remote.UserPagingSource
import com.programmergabut.katili_jiwo_adi_wiyono_takehome.data.remote.api.GithubUsersService
import com.programmergabut.katili_jiwo_adi_wiyono_takehome.data.remote.remoteentity.users.Item
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val githubUsersService: GithubUsersService
): BaseRepository(), Repository {

    /* override suspend fun fetchUsers(query: String, page: String, per_page: String): Deferred<UsersResponse> {
        return CoroutineScope(Dispatchers.IO).async {
            lateinit var response: UsersResponse
            try {

                val call = githubUsersService.fetchGitHubUsers(query, page, per_page)

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
    } */

    override fun getSearchResult(query: String): LiveData<PagingData<Item>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constant.GITHUB_API_PER_PAGE,
                maxSize = Constant.PAGER_MAX_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { UserPagingSource(githubUsersService, query) }
        ).liveData
    }


}