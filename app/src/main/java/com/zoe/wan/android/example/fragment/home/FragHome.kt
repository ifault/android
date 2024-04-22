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
                LogUtils.d("执行到pay")
                viewModel?.pay(uuid) {
                    if (it) {
                        adapter.notifyCollectChange(position)
                    }
                }
            }

            override fun startMonitor() {
                LogUtils.d("执行到startMonitor")
                viewModel?.startMonitor()
            }
        })


        binding?.startMonitor?.setOnClickListener {
            NotificationUtils.sendNotification(requireActivity(), "Notification Title", "Notification Message")
        }

        refresh()
    }
    private val notificationId: Int = 0;
    private fun requestNotificationPermission() {
        val requestCode = 1 // 可以自定义请求码
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.POST_NOTIFICATIONS),
            requestCode
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == 1) { // 请求码匹配
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 用户授予了通知权限，发送通知
                sendNotification()
            } else {
                // 用户拒绝了通知权限，可以显示一个提示信息或其他处理逻辑
                showPermissionDeniedMessage()
            }
        }
    }

    private fun sendNotification() {
        // 构建和发送通知的代码
        val builder: NotificationCompat.Builder = NotificationCompat.Builder(requireContext(), "channel_id")
            .setSmallIcon(com.zoe.wan.android.example.R.drawable.ic_launcher_background)
            .setContentTitle("Notification Title")
            .setContentText("id.message")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        val notificationManager = NotificationManagerCompat.from(requireContext())
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        notificationManager.notify(0, builder.build())
    }
    private fun showPermissionDeniedMessage() {
        val message = "Notification permission is required to send notifications. Please grant the permission from the app settings."
        val builder = AlertDialog.Builder(requireContext())
            .setTitle("Permission Denied")
            .setMessage(message)
            .setPositiveButton("Open Settings") { dialog, _ ->
                openAppSettings()
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
        val dialog = builder.create()
        dialog.show()
    }
    private fun openAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", requireContext().packageName, null)
        intent.data = uri
        startActivity(intent)
    }
}
