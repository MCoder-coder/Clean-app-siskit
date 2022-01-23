package com.example.app_base_siskit.feature_geo_contact.domain.usecase

import com.example.app_base_siskit.feature_geo_contact.data.remote.dto.UserDto
import com.example.app_base_siskit.feature_geo_contact.domain.entity.UserEntity
import com.example.app_base_siskit.feature_geo_contact.domain.repository.GeoUserRepository
import com.example.app_base_siskit.feature_login.data.common.utils.DefaultResponse
import com.example.app_base_siskit.feature_login.presentation.common.BaseResult
import kotlinx.coroutines.flow.Flow

import javax.inject.Inject

class GetUserAllUseCase  @Inject constructor(private val  getUserRepository: GeoUserRepository) {
    suspend fun invoke() : Flow<BaseResult<List<UserEntity>, DefaultResponse<UserDto>>> {
        return getUserRepository.getAllUser()
    }
}