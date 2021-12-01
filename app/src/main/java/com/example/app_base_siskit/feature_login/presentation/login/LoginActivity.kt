package com.example.app_base_siskit.feature_login.presentation.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.app_base_siskit.MainActivity
import com.example.app_base_siskit.R
import com.example.app_base_siskit.databinding.ActivityLoginBinding
import com.example.app_base_siskit.databinding.ActivityMainBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var bindingLogin: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingLogin = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(bindingLogin.root)

        bindingLogin.login.setOnClickListener {
            nextActivity()
        }

    }



    private fun nextActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}