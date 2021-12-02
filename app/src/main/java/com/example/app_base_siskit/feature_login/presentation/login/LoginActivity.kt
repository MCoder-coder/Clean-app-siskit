package com.example.app_base_siskit.feature_login.presentation.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.app_base_siskit.MainActivity
import com.example.app_base_siskit.databinding.ActivityLoginBinding
import com.example.app_base_siskit.feature_login.data.common.utils.WrappedResponse
import com.example.app_base_siskit.feature_login.data.login.remote.dto.Data
import com.example.app_base_siskit.feature_login.data.login.remote.dto.LoginParam
import com.example.app_base_siskit.feature_login.data.login.remote.dto.LoginResponse
import com.example.app_base_siskit.feature_login.domain.login.entity.LoginEntity
import com.example.app_base_siskit.feature_login.presentation.common.isEmail
import com.example.app_base_siskit.feature_login.presentation.common.showGenericAlertDialog
import com.example.app_base_siskit.feature_login.presentation.common.showToast
import com.example.app_base_siskit.utils.SharedPrefs
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var bindingLogin: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    @Inject
    lateinit var sharedPrefs: SharedPrefs

    private val openRegisterActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            goToMainActivity()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingLogin = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(bindingLogin.root)

        login()
        observe()

    }




    private fun login(){
           bindingLogin.actionLogin.setOnClickListener {
                val email = bindingLogin.email.text.toString().trim()
                val password = bindingLogin.password.text.toString().trim()
               if(validate(email, password)){
                   val loginParam = LoginParam(email, password)
                   viewModel.login( loginParam)
               }

            }
    }

    private fun observe(){
        viewModel.mState
            .flowWithLifecycle(lifecycle,  Lifecycle.State.STARTED)
            .onEach { state -> handleStateChange(state) }
            .launchIn(lifecycleScope)
    }

    private fun validate(email: String, password: String) : Boolean{
        resetAllInputError()
        if(!email.isEmail()){
            setEmailError("Email no valido")
            return false
        }

        if(password.length < 8){
            setPasswordError("Paswword no valido")
            return false
        }

        return true
    }

    private fun resetAllInputError(){
        setEmailError(null)
        setPasswordError(null)
    }

    private fun setEmailError(e : String?){

        bindingLogin.email.error = e
    }

    private fun setPasswordError(e: String?){
        bindingLogin.password.error = e
    }

    private fun handleStateChange(state: LoginActivityState){

        when(state){
            is LoginActivityState.Init -> Unit
            is LoginActivityState.ErrorLogin -> Log.i("Tag" , handleErrorLogin(state.rawResponse).toString())
            is LoginActivityState.SuccessLogin -> handleSuccessLogin(state.loginEntity)
            is LoginActivityState.ShowToast -> showToast(state.message)
            is LoginActivityState.IsLoading -> handleLoading(state.isLoading)
        }
    }
    private fun handleSuccessLogin(loginEntity: LoginEntity){
        //sharedPrefs.saveToken(loginEntity.email)
        showToast("Bienvenido ${loginEntity.email}")
        goToMainActivity()
    }
    private fun handleLoading(isLoading: Boolean){
        bindingLogin.actionLogin.isEnabled = !isLoading
        bindingLogin.actionLogin.isEnabled = !isLoading
        bindingLogin.loadingProgressBar.isIndeterminate = isLoading
        if(!isLoading){
            bindingLogin.loadingProgressBar.progress = 0
        }
    }

    private fun handleErrorLogin(response: WrappedResponse<LoginResponse>){
        showGenericAlertDialog(response.mensaje)
    }
    private fun goToMainActivity(){
        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
        finish()
    }

}