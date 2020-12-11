package com.example.virtual_assigment.ui.auth

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.virtual_assigment.R
import com.example.virtual_assigment.base.BaseActivity
import com.example.virtual_assigment.databinding.ActivityAuthBinding
import com.example.virtual_assigment.network.wrapper.ApiResponse
import com.example.virtual_assigment.network_model.AuthRequest
import timber.log.Timber

class AuthActivity : BaseActivity<ActivityAuthBinding>() {

    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding.lifecycleOwner
        viewModel = ViewModelProvider(this, viewModelFactory).get(AuthViewModel::class.java)

        dataBinding.btnLogin.setOnClickListener {
            observeViewModel()
        }
    }

    private fun observeViewModel() {
        val authRequest = AuthRequest(
            dataBinding.editTexUserName.text.toString(),
            dataBinding.editTextPassword.text.toString())

        viewModel.userAuth(authRequest).observe(this, Observer {apiResponse->
            when(apiResponse){
                is ApiResponse.Success->{
                    Timber.e(apiResponse.data.toString())
                    if (apiResponse.data.success){
                        //
                    }
                }
                is ApiResponse.Progress->{}
                is ApiResponse.Failure->{
                    Timber.e(apiResponse.errorMessage.localizedMessage)
                }

                else -> {}
            }
        })

        viewModel.ioError.observe(this, Observer {
            showMessage(it)
        })
    }

    override fun layoutRes(): Int = R.layout.activity_auth
}