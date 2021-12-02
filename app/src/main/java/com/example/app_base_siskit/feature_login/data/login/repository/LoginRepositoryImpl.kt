package com.example.app_base_siskit.feature_login.data.login.repository

import android.util.Log
import com.example.app_base_siskit.feature_login.data.common.utils.WrappedResponse
import com.example.app_base_siskit.feature_login.data.login.remote.api.LoginApi
import com.example.app_base_siskit.feature_login.data.login.remote.dto.LoginParam
import com.example.app_base_siskit.feature_login.data.login.remote.dto.LoginResponse
import com.example.app_base_siskit.feature_login.domain.login.entity.LoginEntity
import com.example.app_base_siskit.feature_login.domain.login.repository.LoginRepository
import com.example.app_base_siskit.feature_login.presentation.common.BaseResult
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(private val loginApi: LoginApi) : LoginRepository {
    override suspend fun login(loginParam: LoginParam): Flow<BaseResult<LoginEntity, WrappedResponse<LoginResponse>>> {
        return flow {
            val response = loginApi.postLogin(email = loginParam.email , password = loginParam.password)
            if (response.isSuccessful){
                val body = response.body()!!
                val loginEntity = LoginEntity(loginParam.email , loginParam.password  )
                 val emit = emit(BaseResult.Success(loginEntity))
                Log.i("Tag emit" ,BaseResult.Success(loginEntity).toString())
            }else{
                val type = object : TypeToken<WrappedResponse<LoginResponse>>(){}.type
                val err : WrappedResponse<LoginResponse> = Gson().fromJson(response.errorBody()!!.charStream(), type)
                err.mensaje = response.message()
                emit(BaseResult.Error(err))
            }
        }
    }
}