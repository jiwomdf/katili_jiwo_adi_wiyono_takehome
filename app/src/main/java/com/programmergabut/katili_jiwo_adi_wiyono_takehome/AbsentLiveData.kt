package com.programmergabut.katili_jiwo_adi_wiyono_takehome

/*
   Created by Katili Jiwo A.W. 13 January 2021
*/

import androidx.lifecycle.LiveData

class AbsentLiveData<T : Any?> private constructor() : LiveData<T>() {
    init {
        postValue(null)
    }
    companion object {
        fun <T> create() : LiveData<T> {
            return AbsentLiveData()
        }
    }
}