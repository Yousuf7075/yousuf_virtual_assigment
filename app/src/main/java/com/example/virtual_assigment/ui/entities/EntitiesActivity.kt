package com.example.virtual_assigment.ui.entities


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.virtual_assigment.R
import com.example.virtual_assigment.base.BaseActivity
import com.example.virtual_assigment.databinding.ActivityEntitiesBinding
import com.example.virtual_assigment.helper.getRealPathFromURI
import com.example.virtual_assigment.network.wrapper.ApiResponse
import com.example.virtual_assigment.network_model.entities.CvFileRQ
import com.example.virtual_assigment.network_model.entities.EntitiesRequest
import com.example.virtual_assigment.util.PermissionHandler
import timber.log.Timber
import java.io.File
import java.util.*


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class EntitiesActivity : BaseActivity<ActivityEntitiesBinding>() {

    override fun layoutRes(): Int = R.layout.activity_entities

    private val fileRequestCode = 123
    private lateinit var cvFile: File


    private lateinit var viewModel: EntitiesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding.lifecycleOwner
        viewModel = ViewModelProvider(this, viewModelFactory).get(EntitiesViewModel::class.java)

        cvFile = File("null")
        dataBinding.btnAttchFile.setOnClickListener {
            if(PermissionHandler.checkPermissionForReadExternalStorage(this)){
                selectFile()
            }else{
                PermissionHandler.requestPermissionForReadExternalStorage(this)
            }
        }

        dataBinding.btnSubmit.setOnClickListener {
            postEntities()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == fileRequestCode){
            val filePath = data?.data!!
            cvFile = File(getRealPathFromURI(applicationContext, filePath))
            dataBinding.cvFileNameTv.text = cvFile.name
            dataBinding.cvFileNameTv.visibility = View.VISIBLE
        }
    }

    private fun selectFile() {
        val intent = Intent()
        intent.type = "application/pdf"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Pdf"), fileRequestCode)
    }

    private fun postEntities() {
        val uniqueID: String = UUID.randomUUID().toString()
        val onSpotCreationTime = System.currentTimeMillis()
        val onSpotUpdateTime = System.currentTimeMillis()

        val cvFileRQ = CvFileRQ(uniqueID)

        val cgpa: Double = if (dataBinding.editTextCgpa.text.toString().isEmpty()){
            0.0
        }else{
            dataBinding.editTextCgpa.text.toString().toDouble()
        }

        val expectedSalary: Int = if (dataBinding.editTextExpectedSalary.text.toString().isEmpty()){
            0
        }else{
            dataBinding.editTextExpectedSalary.text.toString().toInt()
        }

        val experienceInMonth: Int = if (dataBinding.editTextExperienceInMonth.text.toString().isEmpty()){
            0
        }else{
            dataBinding.editTextExperienceInMonth.text.toString().toInt()
        }

        val graduationYear: Int = if (dataBinding.editTextGraduationYear.text.toString().isEmpty()){
            0
        }else{
            dataBinding.editTextGraduationYear.text.toString().toInt()
        }

        val entitiesRequest = EntitiesRequest(
            dataBinding.editTextApplyingIn.text.toString(),
            cgpa,
            dataBinding.editTextCurrentWorkPlaceName.text.toString(),
            cvFileRQ,
            dataBinding.editTextUserEmail.text.toString(),
            expectedSalary,
            experienceInMonth,
            dataBinding.editTextFieldBuzzReference.text.toString(),
            dataBinding.editTexAddress.text.toString(),
            dataBinding.editTextGithubProjectUrl.text.toString(),
            graduationYear,
            dataBinding.editTextUserName.text.toString(),
            dataBinding.editTextUniversityName.text.toString(),
            onSpotCreationTime,
            onSpotUpdateTime,
            dataBinding.editTextPhoneNumber.text.toString(),
            uniqueID)

        viewModel.postEntities(entitiesRequest).observe(this, { apiResponse ->
            when (apiResponse) {
                is ApiResponse.Success -> {
                    showProgressBar(false, dataBinding.progressBar)
                    if (apiResponse.data.success) {
                        uploadFileToServer(apiResponse.data.cvFile.id)
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

    private fun uploadFileToServer(file_token_id: Int) {

        viewModel.uploadFileToServer(file_token_id, cvFile).observe(this, { apiResponse ->
            when (apiResponse) {
                is ApiResponse.Success -> {
                    showProgressBar(false, dataBinding.progressBar)
                    if (apiResponse.data.success){
                        Toast.makeText(applicationContext, apiResponse.data.message, Toast.LENGTH_SHORT).show()
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
}