package com.programmergabut.katili_jiwo_adi_wiyono_takehome.base

import java.lang.Exception

abstract class BaseResponse {
    var statusResponse : String = ""
    var exception: Exception = Exception()
}
