package com.example.virtual_assigment.ui.auth

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.virtual_assigment.R
import com.example.virtual_assigment.base.BaseActivity
import com.example.virtual_assigment.databinding.ActivityAuthBinding
import com.example.virtual_assigment.network.wrapper.ApiResponse
import com.example.virtual_assigment.network_model.auth.AuthRequest
import com.example.virtual_assigment.util.ScreenNavigator
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
            dataBinding.editTextPassword.text.toString()
        )

        viewModel.userAuth(authRequest).observe(this, { apiResponse ->
            when (apiResponse) {
                is ApiResponse.Success -> {
                    showProgressBar(false, dataBinding.progressBar)
                    if (apiResponse.data.success) {
                        Toast.makeText(applicationContext, "Login Success", Toast.LENGTH_SHORT)
                            .show()
                        ScreenNavigator.navigateToInformationCollectActivity(this)
                    }
                }
                is ApiResponse.Progress -> {
                    showProgressBar(true, dataBinding.progressBar)
                }
                is ApiResponse.Failure -> {
                    showProgressBar(false, dataBinding.progressBar)
                    showMessage(apiResponse.errorMessage.localizedMessage)
                }

                else -> {
                }
            }
        })

        viewModel.ioError.observe(this, {
            showMessage(it)
        })
    }

    override fun layoutRes(): Int = R.layout.activity_auth
}