package com.example.app_base_siskit.feature_login.domain.login.repository

import com.example.app_base_siskit.feature_login.data.common.utils.WrappedResponse
import com.example.app_base_siskit.feature_login.data.login.remote.dto.LoginParam
import com.example.app_base_siskit.feature_login.data.login.remote.dto.LoginResponse
import com.example.app_base_siskit.feature_login.data.login.utils.WrappedResponseLogin
import com.example.app_base_siskit.feature_login.domain.login.entity.LoginEntity
import com.example.app_base_siskit.feature_login.presentation.common.BaseResult
import kotlinx.coroutines.flow.Flow




 interface LoginRepository {
    suspend fun login(loginParam: LoginParam) : Flow<BaseResult<LoginEntity, WrappedResponseLogin<LoginResponse>>>
}