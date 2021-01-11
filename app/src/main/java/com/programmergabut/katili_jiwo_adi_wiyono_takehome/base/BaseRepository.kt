package com.programmergabut.katili_jiwo_adi_wiyono_takehome.base

import android.util.Log
import com.google.gson.Gson
import com.programmergabut.katili_jiwo_adi_wiyono_takehome.BuildConfig
import retrofit2.Call

abstract class BaseRepository {
    protected fun<T : BaseResponse> execute(call : Call<T>) : T {
        try{
            val response = call.execute()
            return when(response.isSuccessful){
                true -> {
                    if(BuildConfig.BUILD_TYPE == ("debug"))
                        Log.d("<RES>", Gson().toJson(response.body()!!))
                    response.body()!!
                }
                false -> {
                    if(BuildConfig.BUILD_TYPE == "debug")
                        Log.d("<RES>", response.message())
                    throw Exception()
                }
            }
        }
        catch (e : Exception){
            if(BuildConfig.BUILD_TYPE == "debug")
                e.message?.let {
                    Log.d("<RES>", it)
                }
            throw e
        }
    }
}