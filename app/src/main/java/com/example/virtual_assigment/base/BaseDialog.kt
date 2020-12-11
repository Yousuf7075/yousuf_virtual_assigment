package com.example.virtual_assigment.base

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.virtual_assigment.di.ScreenScope
import com.example.virtual_assigment.helper.SharedPrefHelper
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

@ScreenScope
abstract class BaseDialog<D : ViewDataBinding> : DialogFragment(){

    @Inject lateinit var applicationContext: Application
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject lateinit var sharedPrefHelper: SharedPrefHelper
    private lateinit var dataBinding:D
    private var activityContext: Activity? = null

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dataBinding = DataBindingUtil.inflate(inflater,layoutRes(),container,false)
        activityContext = activity
        return dataBinding.root
    }

    @LayoutRes
    protected abstract fun layoutRes():Int

}