package com.example.app_base_siskit.feature_login.domain.login.repository

import com.example.app_base_siskit.feature_login.data.common.utils.WrappedResponse
import com.example.app_base_siskit.feature_login.data.login.remote.dto.LoginDto
import com.example.app_base_siskit.feature_login.data.login.remote.dto.LoginRequest
import com.example.app_base_siskit.feature_login.domain.login.entity.LoginEntity
import com.example.app_base_siskit.feature_login.presentation.common.BaseResult
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.flow.Flow




 interface LoginRepository {
    suspend fun login(loginRequest: LoginRequest) : Flow<BaseResult<LoginEntity, WrappedResponse<LoginRequest>>>
}