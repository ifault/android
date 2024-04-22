package com.zoe.wan.android.fragment.settting

import android.view.View
import android.view.View.OnClickListener
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SPUtils
import com.google.gson.Gson
import com.zoe.wan.android.example.R
import com.zoe.wan.android.example.BR
import com.zoe.wan.android.example.common.Constants
import com.zoe.wan.android.example.databinding.FragmentSettingBinding
import com.zoe.wan.android.example.repository.Repository
import com.zoe.wan.android.example.repository.data.HomeListData
import com.zoe.wan.android.example.repository.data.SettingData
import com.zoe.wan.base.BaseFragment
import com.zoe.wan.base.loading.LoadingUtils

/**
 * 首页
 */
class FragSetting : BaseFragment<FragmentSettingBinding, SettingViewModel>() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_setting
    }

    override fun getViewModelId(): Int {
        return BR.fragSettingVm
    }

    override fun initViewData() {
        binding?.saveAction?.setOnClickListener {
            LogUtils.d("保存配置")
            LoadingUtils.showLoading()
            val data = SettingData(binding?.fragSettingVm?.server?.get(),binding?.fragSettingVm?.token?.get(),binding?.fragSettingVm?.password?.get() )
            LogUtils.d(GsonUtils.toJson(data))
            SPUtils.getInstance().put(Constants.SP_SETTINGS, GsonUtils.toJson(data))
            viewModel?.addAccount()
        }

        binding?.clearAccounts?.setOnClickListener{
            LogUtils.d("清空账号")
            viewModel?.clearAccount()
        }
    }

    override fun runTask() {

    }

}
