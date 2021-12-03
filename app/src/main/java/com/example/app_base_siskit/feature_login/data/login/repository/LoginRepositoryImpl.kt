package com.example.app_base_siskit.feature_login.data.login.repository

import android.util.Log
import com.example.app_base_siskit.feature_login.data.common.utils.WrappedResponse
import com.example.app_base_siskit.feature_login.data.login.remote.api.LoginApi
import com.example.app_base_siskit.feature_login.data.login.remote.dto.LoginParam
import com.example.app_base_siskit.feature_login.data.login.remote.dto.LoginResponse
import com.example.app_base_siskit.feature_login.data.login.utils.WrappedResponseLogin
import com.example.app_base_siskit.feature_login.domain.login.entity.LoginEntity
import com.example.app_base_siskit.feature_login.domain.login.repository.LoginRepository
import com.example.app_base_siskit.feature_login.presentation.common.BaseResult
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(private val loginApi: LoginApi) : LoginRepository {
    override suspend fun login(loginParam: LoginParam): Flow<BaseResult<LoginEntity, WrappedResponseLogin<LoginResponse>>> {
        return flow {
            val response = loginApi.postLogin(email = loginParam.email , password = loginParam.password)
            if (response.isSuccessful){
                val body = response.body()!!
                val bodyResponse = response.body()!!.data
                val loginEntity = LoginEntity(loginParam.email , loginParam.password , bodyResponse.hash ,bodyResponse.id , body.data.nombre)
                 val emit = emit(BaseResult.Success(loginEntity))
                Log.i("Tag emit" ,BaseResult.Success(loginEntity).toString())
            }else{
                val type = object : TypeToken<WrappedResponseLogin<LoginResponse>>(){}.type
                val err : WrappedResponseLogin<LoginResponse> = Gson().fromJson(response.errorBody()!!.toString(), type)
                err.mensaje = response.message()
                emit(BaseResult.Error(err))
                Log.i("Tag BaseResult.Errort" ,err.mensaje)
            }
        }
    }
}