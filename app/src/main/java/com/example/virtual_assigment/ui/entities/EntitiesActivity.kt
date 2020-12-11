package com.example.virtual_assigment.ui.entities


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.virtual_assigment.R
import com.example.virtual_assigment.base.BaseActivity
import com.example.virtual_assigment.databinding.ActivityEntitiesBinding
import com.example.virtual_assigment.network.wrapper.ApiResponse
import com.example.virtual_assigment.network_model.entities.CvFileRQ
import com.example.virtual_assigment.network_model.entities.EntitiesRequest
import timber.log.Timber
import java.util.*

class EntitiesActivity : BaseActivity<ActivityEntitiesBinding>() {

    override fun layoutRes(): Int = R.layout.activity_entities

    private val fileRequestCode = 123

    private lateinit var viewModel: EntitiesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding.lifecycleOwner
        viewModel = ViewModelProvider(this, viewModelFactory).get(EntitiesViewModel::class.java)

        dataBinding.btnAddFile.setOnClickListener {
            selectFile()
        }

        dataBinding.btnSubmit.setOnClickListener {
            postEntities()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == fileRequestCode){
            val filepath: Uri = data?.data!!
            Timber.e(filepath.toString())
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
        val cvFileRQ = CvFileRQ(uniqueID)
        val entitiesRequest = EntitiesRequest(
                dataBinding.editTextApplyingIn.text.toString(),
                dataBinding.editTextCgpa.text.toString().toDouble(),
                dataBinding.editTextCurrentWorkPlaceName.text.toString(),
                cvFileRQ,
                dataBinding.editTextUserEmail.text.toString(),
                dataBinding.editTextExpectedSalary.text.toString().toInt(),
                dataBinding.editTextExperienceInMonth.text.toString().toInt(),
                dataBinding.editTextFieldBuzzReference.text.toString(),
                dataBinding.editTexAddress.text.toString(),
                dataBinding.editTextGithubProjectUrl.text.toString(),
                dataBinding.editTextGraduationYear.text.toString().toInt(),
                dataBinding.editTextUserName.text.toString(),
                dataBinding.editTextUniversityName.text.toString(),
                1605644298704,
                1605644298702,
                dataBinding.editTextPhoneNumber.text.toString(),
                uniqueID)

        viewModel.postEntities(entitiesRequest).observe(this, { apiResponse->
            when(apiResponse){
                is ApiResponse.Success->{
                    showProgressBar(false, dataBinding.progressBar)
                    Timber.e(apiResponse.data.toString())
                }
                is ApiResponse.Progress->{
                    showProgressBar(true, dataBinding.progressBar)
                }
                is ApiResponse.Failure->{
                    showProgressBar(false, dataBinding.progressBar)
                    showMessage(apiResponse.errorMessage.localizedMessage)
                }
                else -> {}
            }

        })

        viewModel.ioError.observe(this, {
            showMessage(it)
        })
    }
}