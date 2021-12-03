package com.example.app_base_siskit.feature_login.domain.login.usecase

import com.example.app_base_siskit.feature_login.data.login.remote.dto.LoginRequest
import com.example.app_base_siskit.feature_login.data.login.remote.dto.LoginResponseBase
import com.example.app_base_siskit.feature_login.data.login.remote.dto.LoginResponse
import com.example.app_base_siskit.feature_login.domain.login.entity.LoginEntity
import com.example.app_base_siskit.feature_login.domain.login.repository.LoginRepository
import com.example.app_base_siskit.feature_login.presentation.common.BaseResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class LoginUseCase  @Inject constructor(private val loginRepository: LoginRepository) {
    suspend fun invoke(loginRequest: LoginRequest): Flow<BaseResult<LoginEntity, LoginResponse<LoginResponseBase>>> {
        return loginRepository.login( loginRequest)
    }

}