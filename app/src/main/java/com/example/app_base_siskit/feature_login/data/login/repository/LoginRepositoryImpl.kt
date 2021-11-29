package com.example.app_base_siskit.feature_login.data.login.repository

import android.util.Log
import com.example.app_base_siskit.feature_login.data.common.utils.WrappedResponse
import com.example.app_base_siskit.feature_login.data.login.remote.api.LoginApi
import com.example.app_base_siskit.feature_login.data.login.remote.dto.LoginDto
import com.example.app_base_siskit.feature_login.data.login.remote.dto.LoginRequest
import com.example.app_base_siskit.feature_login.domain.login.entity.LoginEntity
import com.example.app_base_siskit.feature_login.domain.login.repository.LoginRepository
import com.example.app_base_siskit.feature_login.presentation.common.BaseResult
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(private val loginApi: LoginApi) : LoginRepository {
    override suspend fun login(loginRequest: LoginRequest): Flow<BaseResult<LoginEntity, WrappedResponse<LoginRequest>>> {
        return flow {
            val response = loginApi.postLogin(email = loginRequest.email , password = loginRequest.password)
            if (response.isSuccessful){
                val body = response.body()!!
                val loginEntity = LoginEntity(body.data.email , body.data.password)
                 val emit = emit(BaseResult.Success(loginEntity))
                Log.i("Tag emit" ,emit.toString())
            }else{
                val type = object : TypeToken<WrappedResponse<LoginDto>>(){}.type
                val err : WrappedResponse<LoginDto> = Gson().fromJson(response.errorBody()!!.charStream(), type)
                //err.code = response.code()

            }
        }
    }
}