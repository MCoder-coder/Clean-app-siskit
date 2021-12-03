package com.example.app_base_siskit.feature_login.domain.login.repository

import com.example.app_base_siskit.feature_login.data.login.remote.dto.LoginRequest
import com.example.app_base_siskit.feature_login.data.login.remote.dto.LoginResponseBase
import com.example.app_base_siskit.feature_login.data.login.remote.dto.LoginResponse
import com.example.app_base_siskit.feature_login.domain.login.entity.LoginEntity
import com.example.app_base_siskit.feature_login.presentation.common.BaseResult
import kotlinx.coroutines.flow.Flow




 interface LoginRepository {
    suspend fun login(loginRequest: LoginRequest) : Flow<BaseResult<LoginEntity, LoginResponse<LoginResponseBase>>>
}