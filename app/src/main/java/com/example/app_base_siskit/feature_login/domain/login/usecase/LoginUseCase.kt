package com.example.app_base_siskit.feature_login.domain.login.usecase

import com.example.app_base_siskit.feature_login.data.common.utils.WrappedResponse
import com.example.app_base_siskit.feature_login.data.login.remote.dto.LoginParam
import com.example.app_base_siskit.feature_login.data.login.remote.dto.LoginResponse
import com.example.app_base_siskit.feature_login.data.login.utils.WrappedResponseLogin
import com.example.app_base_siskit.feature_login.domain.login.entity.LoginEntity
import com.example.app_base_siskit.feature_login.domain.login.repository.LoginRepository
import com.example.app_base_siskit.feature_login.presentation.common.BaseResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class LoginUseCase  @Inject constructor(private val loginRepository: LoginRepository) {
    suspend fun invoke(loginParam: LoginParam): Flow<BaseResult<LoginEntity, WrappedResponseLogin<LoginResponse>>> {
        return loginRepository.login( loginParam)
    }

}