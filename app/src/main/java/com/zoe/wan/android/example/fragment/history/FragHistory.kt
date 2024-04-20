package com.zoe.wan.android.example.fragment.history

import com.zoe.wan.android.example.R
import com.zoe.wan.base.BaseFragment
import com.zoe.wan.android.example.BR
import com.zoe.wan.android.example.databinding.FragmentHistoryBinding
import com.zoe.wan.android.example.databinding.FragmentHomeBinding

class FragHistory: BaseFragment<FragmentHistoryBinding, FragHistoryViewModel>() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_history
    }

    override fun getViewModelId(): Int {
        return BR.historyVm
    }

    override fun initViewData() {
    }
}