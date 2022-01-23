package com.example.app_base_siskit.feature_geo_contact.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.app_base_siskit.common.GeoDatabase
import com.example.app_base_siskit.feature_geo_contact.data.local.entity.GeoUserEntity
import com.example.app_base_siskit.feature_geo_contact.data.local.entity.asDomainModel
import com.example.app_base_siskit.feature_geo_contact.data.remote.GeoUserAPi
import com.example.app_base_siskit.feature_geo_contact.data.remote.dto.UserDto
import com.example.app_base_siskit.feature_geo_contact.domain.entity.UserModel
import com.example.app_base_siskit.feature_geo_contact.domain.repository.GeoUserRepository
import com.example.app_base_siskit.feature_login.data.common.utils.DefaultResponse
import com.example.app_base_siskit.feature_login.presentation.common.BaseResult
import com.example.app_base_siskit.utils.SharedPrefs
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GeoUserImpl @Inject constructor(private val userAPi: GeoUserAPi) : GeoUserRepository {

    lateinit var sharedPrefs: SharedPrefs

   /* val user: LiveData<List<UserModel>> = Transformations.map(geoDatabase.getUserDao().getAllUser()) {
        it.asDomainModel()
    }*/

    override suspend fun getAllMyProducts(): Flow<BaseResult<List<UserModel>, DefaultResponse<UserDto>>> {
        return flow {
            val response = userAPi.getUser(sharedPrefs.getHash())
            if (response.isSuccessful){
                val body = response.body()!!
                var users = mutableListOf<UserModel>()
                var user: UserModel
                body.data?.forEach { userResponse ->
                    user = UserModel(userResponse.id , userResponse.email ,  userResponse.usuarios_tipos_id , userResponse.sistema_id , userResponse.telefono , userResponse.nombre )
                    users.add(
                        UserModel(
                            userResponse.id , userResponse.email ,  userResponse.usuarios_tipos_id , userResponse.sistema_id , userResponse.telefono , userResponse.nombre
                        )
                    )

                }

                emit(BaseResult.Success(users))

                Log.i("Tag user Emit" , users.toString())
                users.map {
                    GeoUserEntity(
                        id = it.id.toInt(),
                        email = it.email,
                        nombre = it.nombre,
                        telefono = it.telefono,
                        sistema_id = it.sistema_id.toInt(),
                        usuarios_tipos_id = it.usuarios_tipos_id
                    )
                }
               // geoDatabase.getUserDao().insertGeoUser(users)

            }else{
   /*             val type = object : TypeToken<WrappedListResponse<ProductResponse>>(){}.type
                val err = Gson().fromJson<WrappedListResponse<ProductResponse>>(response.errorBody()!!.charStream(), type)!!
                err.code = response.code()
                emit(BaseResult.Error(err))*/
            }
        }
    }

}