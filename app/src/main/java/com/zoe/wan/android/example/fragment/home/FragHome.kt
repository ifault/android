package com.zoe.wan.android.fragment.home

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ToastUtils
import com.youth.banner.util.LogUtils
import com.zoe.wan.android.example.BR
import com.zoe.wan.android.example.R
import com.zoe.wan.android.example.adapter.HomeListAdapter
import com.zoe.wan.android.example.databinding.FragmentHomeBinding
import com.zoe.wan.android.example.repository.data.HomeListItemData
import com.zoe.wan.base.BaseFragment
import com.zoe.wan.base.adapter.AdapterCollectListener


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

    private fun refresh() {
        binding?.homeRefreshView?.setOnRefreshListener {
            viewModel?.initData()
            it.finishRefresh()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initListView() {

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


        binding?.startMonitor?.setOnClickListener {
            viewModel?.startMonitor()
//            NotificationUtils.sendNotification(requireActivity(), "Notification Title", "Notification Message")
        }
        refresh()
    }
}
