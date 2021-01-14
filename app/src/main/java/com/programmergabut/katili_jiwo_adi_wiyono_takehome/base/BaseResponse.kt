package com.programmergabut.katili_jiwo_adi_wiyono_takehome.base

/*
   Created by Katili Jiwo A.W. 11 January 2021
*/

import java.lang.Exception

abstract class BaseResponse {
    var status : String = ""
    var exception: Exception = Exception()
}
