package com.example.app_base_siskit.feature_login.domain.login.usecase

import com.example.app_base_siskit.feature_login.data.common.utils.WrappedResponse
import com.example.app_base_siskit.feature_login.data.login.remote.dto.LoginDto
import com.example.app_base_siskit.feature_login.data.login.remote.dto.LoginRequest
import com.example.app_base_siskit.feature_login.domain.login.entity.LoginEntity
import com.example.app_base_siskit.feature_login.domain.login.repository.LoginRepository
import com.example.app_base_siskit.feature_login.presentation.common.BaseResult
import dagger.Binds
import dagger.Provides
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class LoginUseCase  @Inject constructor(private val loginRepository: LoginRepository) {
    suspend fun invoke(loginRequest: LoginRequest): Flow<BaseResult<LoginEntity, WrappedResponse<LoginRequest>>> {
        return loginRepository.login(loginRequest)
    }

}