package com.example.app_base_siskit.feature_login.presentation.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_base_siskit.feature_login.data.common.utils.WrappedResponse
import com.example.app_base_siskit.feature_login.data.login.remote.dto.LoginRequest
import com.example.app_base_siskit.feature_login.domain.login.entity.LoginEntity
import com.example.app_base_siskit.feature_login.domain.login.usecase.LoginUseCase
import com.example.app_base_siskit.feature_login.presentation.common.BaseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel  @Inject constructor(private val loginUseCase: LoginUseCase): ViewModel()  {

    private val state = MutableStateFlow<LoginActivityState>(LoginActivityState.Init)
    val mState: StateFlow<LoginActivityState> get() = state


    private fun setLoading(){
        state.value = LoginActivityState.IsLoading(true)
    }

    private fun hideLoading(){
        state.value = LoginActivityState.IsLoading(false)
    }

    private fun showToast(message: String){
        state.value = LoginActivityState.ShowToast(message)
    }


    fun login(loginRequest: LoginRequest){
        viewModelScope.launch {
            loginUseCase.invoke(loginRequest)
                .onStart {
                    setLoading()
                }
                .catch { exception ->
                    hideLoading()
                    showToast(exception.message.toString())
                }
                .collect { baseResult ->
                    hideLoading()
                    when(baseResult){

                        is BaseResult.Error ->  LoginActivityState.ErrorLogin(baseResult.rawResponse)
                        is BaseResult.Success ->  LoginActivityState.SuccessLogin(baseResult.data)
                    }
                }

        }
    }


}
sealed class LoginActivityState  {
    object Init : LoginActivityState()
    data class IsLoading(val isLoading: Boolean) : LoginActivityState()
    data class ShowToast(val message: String) : LoginActivityState()
    data class SuccessLogin(val loginEntity: LoginEntity) : LoginActivityState()
    data class ErrorLogin(val rawResponse: WrappedResponse<LoginRequest>) : LoginActivityState()
}
