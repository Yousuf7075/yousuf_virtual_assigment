package com.example.virtual_assigment.ui.entities


import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.lifecycle.ViewModelProvider
import com.example.virtual_assigment.R
import com.example.virtual_assigment.base.BaseActivity
import com.example.virtual_assigment.databinding.ActivityEntitiesBinding
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
    lateinit var filePath: Uri


    private lateinit var viewModel: EntitiesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding.lifecycleOwner
        viewModel = ViewModelProvider(this, viewModelFactory).get(EntitiesViewModel::class.java)

        dataBinding.btnAddFile.setOnClickListener {
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
            filePath = data?.data!!
        }
    }

    private fun selectFile() {
        val intent = Intent()
        intent.type = "application/pdf";
        intent.action = Intent.ACTION_GET_CONTENT;
        startActivityForResult(Intent.createChooser(intent, "Select Pdf"), fileRequestCode);
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
            uniqueID
        )

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
        val file = File(getRealPathFromURI(filePath))
        Timber.e(file.toString())

        viewModel.uploadFileToServer(file_token_id, file).observe(this, { apiResponse ->
            when (apiResponse) {
                is ApiResponse.Success -> {
                    showProgressBar(false, dataBinding.progressBar)
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

    fun getPath(uri: Uri?): String? {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor = managedQuery(uri, projection, null, null, null)
        startManagingCursor(cursor)
        val column_index: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        return cursor.getString(column_index)
    }

    private fun getRealPathFromURI(contentURI: Uri): String? {
        val result: String?
        val cursor = contentResolver.query(contentURI, null, null, null, null)
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.path
        } else {
            cursor.moveToFirst()
            val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            result = cursor.getString(idx)
            cursor.close()
        }
        return result
    }
}