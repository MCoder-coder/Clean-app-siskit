package com.example.app_base_siskit.feature_geo_contact.data.repository

import android.content.Context
import android.util.Log
import com.example.app_base_siskit.common.GeoDatabase
import com.example.app_base_siskit.feature_geo_contact.data.local.entity.GeoUserEntity

import com.example.app_base_siskit.feature_geo_contact.data.remote.GeoUserAPi
import com.example.app_base_siskit.feature_geo_contact.data.remote.dto.UserDto
import com.example.app_base_siskit.feature_geo_contact.domain.entity.UserEntity
import com.example.app_base_siskit.feature_geo_contact.domain.repository.GeoUserRepository
import com.example.app_base_siskit.feature_login.data.common.utils.DefaultResponse
import com.example.app_base_siskit.feature_login.presentation.common.BaseResult
import com.example.app_base_siskit.utils.SharedPrefs
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GeoUserRepositoryImpl @Inject constructor(private val userAPi: GeoUserAPi , context: Context) : GeoUserRepository {


    private val geoDatabase : GeoDatabase = GeoDatabase.getInstance(context)!!
/*    val asDomainModelUser: LiveData<List<UserEntity>> = Transformations.map(geoDatabase.getUserDao().getAllUser()) {
        it.asDomainModel()
    }*/
    lateinit var sharedPrefs: SharedPrefs
    override suspend fun getAllUser(): Flow<BaseResult<List<UserEntity>, DefaultResponse<UserDto>>>  {

            return  flow {
                 val response = userAPi.getUser()
                response.body()!!.data!!.map {
                   val db =  GeoUserEntity(
                        it.id,
                        it.email,
                        it.nombre,
                        it.sistema_id,
                        it.telefono,
                        it.usuarios_tipos_id
                    )
                    geoDatabase.getUserDao().insertGeoUser(db)
                }

                Log.i("tag", response.toString())
                if (response.isSuccessful) {
                    val body = response.body()!!
                    Log.i("tag", body.toString())
                    val users = mutableListOf<UserEntity>()
                    var user: UserEntity?
                    body.data?.forEach { usersResponse ->
                        user = UserEntity(  usersResponse.id, usersResponse.email, usersResponse.nombre , usersResponse.sistema_id, usersResponse.telefono , usersResponse.usuarios_tipos_id)
                        users.add(
                            UserEntity(
                                usersResponse.id,
                                usersResponse.email,
                                usersResponse.nombre,
                                usersResponse.sistema_id,
                                usersResponse.telefono,
                                usersResponse.usuarios_tipos_id
                            )
                        )
                        Log.i("id", usersResponse.id.toString())
                        val insert = GeoUserEntity(
                            usersResponse.id,
                            usersResponse.email,
                            usersResponse.nombre,
                            usersResponse.sistema_id,
                            usersResponse.telefono,
                            usersResponse.usuarios_tipos_id
                        )
                        geoDatabase.getUserDao().insertGeoUser(insert)

                    }


                    emit(BaseResult.Success(users))


                } else {
                    val type = object : TypeToken<DefaultResponse<UserDto>>() {}.type
                    val err = Gson().fromJson<DefaultResponse<UserDto>>(
                        response.errorBody()!!.charStream(), type
                    )!!
                    err.data = response.body()!!.data
                    // emit(BaseResult.Error(err))
                }

            }

    }


}