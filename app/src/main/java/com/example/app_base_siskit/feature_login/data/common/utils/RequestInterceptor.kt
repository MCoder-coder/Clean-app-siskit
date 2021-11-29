package com.example.app_base_siskit.feature_login.data.common.utils

import com.example.app_base_siskit.utils.SharedPrefs
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor

class RequestInterceptor    {

  companion object{
      val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
          this.level = HttpLoggingInterceptor.Level.BODY
      }
  }

}
