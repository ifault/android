package com.zoe.wan.android.fragment.settting

import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SPUtils
import com.zoe.wan.android.jiangnan.R
import com.zoe.wan.android.jiangnan.BR
import com.zoe.wan.android.jiangnan.common.Constants
import com.zoe.wan.android.jiangnan.databinding.FragmentSettingBinding
import com.zoe.wan.base.BaseFragment
import com.zoe.wan.base.loading.LoadingUtils

/**GET
 * POST /order/api/order/create 获取订单号
 * data->orderNumber=SC010978986844362752
 * /order/api/order/SC010978893610029056 获取sessionid
 * data->paymentSessionId=84f72563c70d854aaa65ecf12702eb3c
 * 首页POST /payment-middleware-service/session/91af8c4bc9c06411133ce09077c56939/transactions HTTP/1.1
 * params->response_text
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
            SPUtils.getInstance().put(Constants.SP_SETTINGS_SERVER, binding?.fragSettingVm?.server?.get())
            SPUtils.getInstance().put(Constants.SP_SETTINGS_TOKEN, binding?.fragSettingVm?.token?.get())
            SPUtils.getInstance().put(Constants.SP_SETTINGS_PASSWORD, binding?.fragSettingVm?.password?.get())
            SPUtils.getInstance().put(Constants.SP_SETTINGS_EXPIRED_TIME, binding?.fragSettingVm?.expiredTime?.get() ?: "1")
            viewModel?.addAccount()
        }

        binding?.clearAccounts?.setOnClickListener{
            LogUtils.d("清空账号")
            viewModel?.clearAccount()
        }

        binding?.verify?.setOnClickListener{
            viewModel?.verify()
        }
    }

    override fun runTask() {

    }

}
