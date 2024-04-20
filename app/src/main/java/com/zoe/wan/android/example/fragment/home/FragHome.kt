package com.zoe.wan.android.example.fragment.home

import com.zoe.wan.android.example.R
import com.zoe.wan.base.BaseFragment
import com.zoe.wan.android.example.BR
import com.zoe.wan.android.example.databinding.FragmentHomeBinding

class FragHome: BaseFragment<FragmentHomeBinding, FragHomeViewModel>() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun getViewModelId(): Int {
        return BR.homeVm
    }

    override fun initViewData() {
    }
}