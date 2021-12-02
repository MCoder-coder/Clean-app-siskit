package com.example.app_base_siskit.feature_login.data.login


import com.example.app_base_siskit.di.module.NetworkModule
import com.example.app_base_siskit.feature_login.data.login.remote.api.LoginApi
import com.example.app_base_siskit.feature_login.data.login.repository.LoginRepositoryImpl
import com.example.app_base_siskit.feature_login.domain.login.repository.LoginRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class LoginModule {

    @Singleton
    @Provides
    fun provideLoginApi(retrofit: Retrofit) : LoginApi {
        return retrofit.create(LoginApi::class.java)
    }

    @Singleton
    @Provides
    fun provideLoginRepository(loginApi: LoginApi) : LoginRepository {
        return LoginRepositoryImpl(loginApi)
    }


}