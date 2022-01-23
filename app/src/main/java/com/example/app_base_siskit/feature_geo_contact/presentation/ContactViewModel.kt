package com.example.app_base_siskit.feature_geo_contact.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_base_siskit.common.GeoDatabase
import com.example.app_base_siskit.feature_geo_contact.data.remote.dto.UserDto
import com.example.app_base_siskit.feature_geo_contact.domain.entity.UserEntity
import com.example.app_base_siskit.feature_geo_contact.domain.usecase.GetUserAllUseCase
import com.example.app_base_siskit.feature_login.presentation.common.BaseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactViewModel @Inject constructor(private val getUserAllUseCase: GetUserAllUseCase) : ViewModel() {

    private val state = MutableStateFlow<NewClientFragment>(NewClientFragment.Init)
    val mState: StateFlow<NewClientFragment> get() = state

    private val users = MutableStateFlow<List<UserEntity>>(mutableListOf())
    val Users: StateFlow<List<UserEntity>> get() = users



    private fun setLoading(){
        state.value = NewClientFragment.IsLoading(true)
    }

    private fun hideLoading(){
        state.value = NewClientFragment.IsLoading(false)
    }

    init {
        fetchAllUser()
    }

    fun fetchAllUser(){
        viewModelScope.launch {
            getUserAllUseCase.invoke()
              //showToast(exception.message.toString())
                .onStart {
                    setLoading()
                }
                .catch { exception ->


                }
                .collect { result ->
                    Log.i("Tag asDomainModelUser" , result.toString())
                    when(result){
                        is BaseResult.Success -> {
                            users.value = result.data

                        }
                        is BaseResult.Error -> {
                           // showToast(result.rawResponse.message)
                        }
                    }
                }


        }
    }



    sealed class NewClientFragment {
        object Init : NewClientFragment()
        data class IsLoading(val isLoading: Boolean) : NewClientFragment()
        data class ShowToast(val message : String) : NewClientFragment()
    }

}