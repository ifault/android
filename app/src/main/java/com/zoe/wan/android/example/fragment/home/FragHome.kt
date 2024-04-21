package com.zoe.wan.android.fragment.home

import androidx.recyclerview.widget.LinearLayoutManager
import com.youth.banner.util.LogUtils
import com.zoe.wan.android.example.R
import com.zoe.wan.android.example.BR
import com.zoe.wan.android.example.adapter.HomeListAdapter
import com.zoe.wan.android.example.databinding.FragmentHomeBinding
import com.zoe.wan.android.example.repository.data.HomeListItemData
import com.zoe.wan.base.BaseFragment
import com.zoe.wan.base.loading.LoadingDialog
import com.zoe.wan.base.adapter.AdapterCollectListener
import com.zoe.wan.base.loading.LoadingUtils

/**
 * 首页
 */
class FragHome : BaseFragment<FragmentHomeBinding, HomeViewModel>() {
    private val adapter: HomeListAdapter = HomeListAdapter()

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun getViewModelId(): Int {
        return BR.fragHomeVm
    }

    override fun initViewData() {
        //初始化lieview
        initListView()

        refreshData()
    }

    private fun refreshData() {
        LogUtils.d("刷新数据")
        viewModel?.homeListData?.observe(viewLifecycleOwner) { list ->
            if (list != null && list?.isNotEmpty() == true) {
                binding?.homeTabListView?.post {
                    adapter.setDataList(list)
                }
            }
        }
    }

    override fun runTask() {

    }

    private fun initListView() {
        LoadingUtils.showLoading()
        val manager = LinearLayoutManager(context)
        manager.orientation = LinearLayoutManager.VERTICAL
        binding?.homeTabListView?.layoutManager = manager
        binding?.homeTabListView?.adapter = adapter

        adapter.setCollectListener(object : AdapterCollectListener<HomeListItemData?>() {
            override fun pay(position: Int, uuid: String) {
                viewModel?.pay(uuid) {
                    if (it) {
                        adapter.notifyCollectChange(position)
                    }
                }
            }
        })
        //添加分割线
//        binding?.homeTabListView?.addItemDecoration(
//            DividerItemDecoration(
//                context,
//                LinearLayoutManager.VERTICAL
//            )
//        )
    }

}
