package com.example.app_base_siskit.feature_login.data.login.repository

import android.util.Log
import com.example.app_base_siskit.feature_login.data.login.remote.LoginApi
import com.example.app_base_siskit.feature_login.data.login.remote.dto.LoginRequest
import com.example.app_base_siskit.feature_login.data.login.remote.dto.LoginResponseBase
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
    override suspend fun login(loginRequest: LoginRequest): Flow<BaseResult<LoginEntity, LoginResponse<LoginResponseBase>>> {
        return flow {
            val response = loginApi.postLogin(email = loginRequest.email , password = loginRequest.password)
            if (response.isSuccessful){
                val body = response.body()!!
                val bodyResponse = response.body()!!.loginResponseDataBase
                val loginEntity = LoginEntity(loginRequest.email , loginRequest.password , bodyResponse.hash ,bodyResponse.id , body.loginResponseDataBase.nombre)
                 val emit = emit(BaseResult.Success(loginEntity))
                Log.i("Tag emit" ,BaseResult.Success(loginEntity).toString())
            }else{
                val type = object : TypeToken<LoginResponse<LoginResponseBase>>(){}.type
                val err : LoginResponse<LoginResponseBase> = Gson().fromJson(response.errorBody()!!.toString(), type)
                err.mensaje = response.message()
                emit(BaseResult.Error(err))
                Log.i("Tag BaseResult.Errort" ,err.mensaje)
            }
        }
    }
}