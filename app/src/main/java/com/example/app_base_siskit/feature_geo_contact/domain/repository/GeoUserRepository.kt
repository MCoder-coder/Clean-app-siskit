package com.example.app_base_siskit.feature_geo_contact.domain.repository

import com.example.app_base_siskit.feature_geo_contact.data.remote.dto.UserDto
import com.example.app_base_siskit.feature_geo_contact.domain.entity.UserEntity
import com.example.app_base_siskit.feature_login.data.common.utils.DefaultResponse
import com.example.app_base_siskit.feature_login.presentation.common.BaseResult
import kotlinx.coroutines.flow.Flow


interface GeoUserRepository {
    suspend fun getAllUser() : Flow<BaseResult<List<UserEntity>, DefaultResponse<UserDto>>>
}