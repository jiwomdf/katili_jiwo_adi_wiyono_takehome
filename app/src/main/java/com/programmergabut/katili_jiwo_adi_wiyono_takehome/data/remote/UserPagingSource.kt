package com.programmergabut.katili_jiwo_adi_wiyono_takehome.data.remote

import android.util.Log
import androidx.paging.PagingSource
import com.programmergabut.katili_jiwo_adi_wiyono_takehome.Constant.Companion.GITHUB_API_PER_PAGE
import com.programmergabut.katili_jiwo_adi_wiyono_takehome.Constant.Companion.GITHUB_API_STARTING_PAGE
import com.programmergabut.katili_jiwo_adi_wiyono_takehome.data.remote.api.GithubUsersService
import com.programmergabut.katili_jiwo_adi_wiyono_takehome.data.remote.remoteentity.users.Item
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception

class UserPagingSource(
    private val githubUsersService: GithubUsersService,
    private val query: String
): PagingSource<Int, Item>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Item> {

        val position = params.key ?: GITHUB_API_STARTING_PAGE
        val perPage = GITHUB_API_PER_PAGE

        return try {
            val response = githubUsersService.fetchGitHubUsers(query, position.toString(), perPage.toString())

            when {
                response.isSuccessful -> {
                    val users = response.body()!!.items
                    LoadResult.Page(
                        data = users,
                        prevKey = if (position == GITHUB_API_STARTING_PAGE) null else position - 1,
                        nextKey = if (users.isEmpty()) null else position + 1
                    )
                }
                response.code() == 403 -> {
                    Log.d("<TESTING>", "403")
                    LoadResult.Error(Exception("403"))
                }
                else -> {
                    Log.d("<TESTING>", "else")
                    LoadResult.Error(Exception("else"))
                }
            }

        }
        catch (ex: IOException){
            Log.d("<TESTING>", "IOException")
            LoadResult.Error(ex)
        }
        catch (ex: HttpException){
            Log.d("<TESTING>", "HttpException")
            LoadResult.Error(ex)
        }

    }


}