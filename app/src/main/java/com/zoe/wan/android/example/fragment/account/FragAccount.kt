package com.zoe.wan.android.example.fragment.account

import com.zoe.wan.android.example.R
import com.zoe.wan.android.example.databinding.FragmentAccountBinding
import com.zoe.wan.base.BaseFragment
import com.zoe.wan.android.example.BR

class FragAccount: BaseFragment<FragmentAccountBinding, FragAccountViewModel>() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_account
    }

    override fun getViewModelId(): Int {
        return BR.accountVm
    }

    override fun initViewData() {
        viewModel?.getAccountList()
    }
}