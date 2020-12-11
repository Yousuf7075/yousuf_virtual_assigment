package com.example.virtual_assigment.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.virtual_assigment.helper.SharedPrefHelper
import com.example.virtual_assigment.network.wrapper.ApiResponse
import com.example.virtual_assigment.network_model.auth.AuthRequest
import com.example.virtual_assigment.network_model.auth.AuthResponse
import com.example.virtual_assigment.util.AbsentLiveData
import com.example.virtual_assigment.util.AppConstant
import javax.inject.Inject

class AuthViewModel @Inject constructor(private var repo: AuthRepo, private val shf: SharedPrefHelper): ViewModel() {
    var ioError = MutableLiveData<String>()

    fun userAuth(authRequest: AuthRequest): LiveData<ApiResponse<AuthResponse>> {
        return if (authRequest.username.isEmpty() || authRequest.password.isEmpty()) {
            ioError.value = "user name and password can't be empty"
            AbsentLiveData.create()
        }else{
            Transformations.map(repo.userAuth(authRequest)){
                when (it){
                    is ApiResponse.Success->{
                        shf.putString(AppConstant.USER_AUTH, "Token ${it.data.token}")
                        shf.putString(AppConstant.USER_TYPE, it.data.authInfo.userType)
                    }
                    else -> {}
                }
                it
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        repo.onClear()
    }
}