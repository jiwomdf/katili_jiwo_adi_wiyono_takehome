package com.programmergabut.katili_jiwo_adi_wiyono_takehome.data.remote

/*
   Created by Katili Jiwo A.W. 13 January 2021
*/

import androidx.paging.PagingSource
import com.programmergabut.katili_jiwo_adi_wiyono_takehome.Constant.Companion.GITHUB_API_PER_PAGE
import com.programmergabut.katili_jiwo_adi_wiyono_takehome.Constant.Companion.GITHUB_API_STARTING_PAGE
import com.programmergabut.katili_jiwo_adi_wiyono_takehome.data.Repository
import com.programmergabut.katili_jiwo_adi_wiyono_takehome.data.remote.remoteentity.users.Item

const val RESPONSE_NULL_MSG = "response items is null"
class UserPagingSource(
    private val repository: Repository,
    private val query: String
): PagingSource<Int, Item>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Item> {
        val position = params.key ?: GITHUB_API_STARTING_PAGE
        val perPage = GITHUB_API_PER_PAGE

        return try {
            val response = repository.fetchUsersAsync(query, position.toString(), perPage.toString()).await()
            when(response.status.toInt()){
                1 -> {
                    if(response.items != null){
                        val users = if(response.items.isEmpty()) emptyList() else response.items
                        LoadResult.Page(
                            data = users,
                            prevKey = if (position == GITHUB_API_STARTING_PAGE) null else position - 1,
                            nextKey = if (users.isEmpty()) null else position + 1
                        )
                    }
                    else{
                        LoadResult.Error(Exception(RESPONSE_NULL_MSG))
                    }
                }
                -1 -> {
                    LoadResult.Error(response.exception)
                }
                else -> {
                    LoadResult.Error(response.exception)
                }
            }

        }
        catch (ex: Exception){
            LoadResult.Error(ex)
        }
    }


}