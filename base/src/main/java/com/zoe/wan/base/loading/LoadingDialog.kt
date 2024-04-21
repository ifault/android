package com.zoe.wan.base.loading

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import androidx.databinding.DataBindingUtil
import com.zoe.wan.base.R
import com.zoe.wan.base.databinding.LoadingDialogBinding

class LoadingDialog(private val context: Context) : Dialog(context){
    private var binding: LoadingDialogBinding?=null
    private var animation: Animation? = null

    init{
        binding = DataBindingUtil.inflate(LayoutInflater.from(context),
            R.layout.loading_dialog, null, false)
        setCanceledOnTouchOutside(false)
        animation = AnimationUtils.loadAnimation(context, R.anim.loading)
        animation?.interpolator = LinearInterpolator()
        binding?.root?.let {
            setContentView(it)
        }
        window?.attributes?.gravity  =Gravity.CENTER
    }

    fun showLoading(){
        animation?.let {
            binding?.loadingImg?.startAnimation(animation)
        }
        super.show()
    }

    fun disableLoading(){
        animation?.cancel()
        animation = null
        super.dismiss()
    }
}