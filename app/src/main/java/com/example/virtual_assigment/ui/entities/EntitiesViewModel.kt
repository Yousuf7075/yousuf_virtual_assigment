package com.example.virtual_assigment.ui.entities

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.virtual_assigment.network.wrapper.ApiResponse
import com.example.virtual_assigment.network_model.entities.EntitiesRequest
import com.example.virtual_assigment.network_model.entities.EntitiesResponse
import com.example.virtual_assigment.network_model.upload_file.FileUploadResponse
import com.example.virtual_assigment.util.AbsentLiveData
import java.io.File
import javax.inject.Inject

class EntitiesViewModel  @Inject constructor(private var repo: EntitiesRepo): ViewModel() {
    var ioError = MutableLiveData<String>()

    fun postEntities(
            entitiesRequest: EntitiesRequest):LiveData<ApiResponse<EntitiesResponse>> {
        return when {
            entitiesRequest.name.isEmpty() -> {
                ioError.value = "user name can't be empty"
                AbsentLiveData.create()
            }
            entitiesRequest.email.isEmpty() -> {
                ioError.value = "user email can't be empty"
                AbsentLiveData.create()
            }
            entitiesRequest.phone.isEmpty() ->{
                ioError.value = "user phone number can't be empty"
                AbsentLiveData.create()
            }
            entitiesRequest.fullAddress.isEmpty() ->{
                ioError.value = "user address can't be empty"
                AbsentLiveData.create()
            }
            entitiesRequest.nameOfUniversity.isEmpty() ->{
                ioError.value = "name of university can't be empty"
                AbsentLiveData.create()
            }
            entitiesRequest.graduationYear.toString().isEmpty() ->{
                ioError.value = "graduation year can't be empty"
                AbsentLiveData.create()
            }
            entitiesRequest.cgpa.toString().isEmpty() ->{
                ioError.value = "cgpa can't be empty"
                AbsentLiveData.create()
            }
            entitiesRequest.experienceInMonths.toString().isEmpty() ->{
                ioError.value = "experience in month can't be empty"
                AbsentLiveData.create()
            }
            entitiesRequest.currentWorkPlaceName.isEmpty() ->{
                ioError.value = "current work palce can't be empty"
                AbsentLiveData.create()
            }
            entitiesRequest.applyingIn.isEmpty() ->{
                ioError.value = "applying In can't be empty"
                AbsentLiveData.create()
            }
            entitiesRequest.expectedSalary.toString().isEmpty() ->{
                ioError.value = "expected salary can't be empty"
                AbsentLiveData.create()
            }
            entitiesRequest.fieldBuzzReference.isEmpty() ->{
                ioError.value = "fieldBuzz reference can't be empty"
                AbsentLiveData.create()
            }
            entitiesRequest.githubProjectUrl.isEmpty() ->{
                ioError.value = "github project url can't be empty"
                AbsentLiveData.create()
            }
            else -> {
                return repo.postEntities(entitiesRequest)
            }
        }
    }

    fun uploadFileToServer(file_token_id: Int, file:File): LiveData<ApiResponse<FileUploadResponse>>{
        return if (!file.exists()){
            ioError.value = "file field can't be empty"
            AbsentLiveData.create()
        }else{
            repo.uploadFile(file_token_id, file)
        }
    }

}