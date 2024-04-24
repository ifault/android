package com.zoe.wan.android.jiangnan.activity.home

import android.graphics.BitmapFactory
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.zoe.wan.android.jiangnan.BR
import com.zoe.wan.android.jiangnan.R
import com.zoe.wan.android.jiangnan.databinding.ActivityTabBinding
import com.zoe.wan.android.fragment.home.FragHome
import com.zoe.wan.android.fragment.settting.FragSetting
import com.zoe.wan.base.BaseActivity
import com.zoe.wan.base.adapter.Pager2Adapter
import com.zoe.wan.base.tab.NavigationBottomBar

class TabActivity : BaseActivity<ActivityTabBinding, TabViewModel>() {
    override fun getLayoutId(): Int {
        return R.layout.activity_tab
    }

    override fun getViewModelId(): Int {
        return BR.homeVm
    }

    override fun initViewData() {
        initPage()
        val selected = arrayOf(
            BitmapFactory.decodeResource(resources, R.drawable.icon_community_selected),
            BitmapFactory.decodeResource(resources, R.drawable.icon_home_selected),
        )
        val unSelected = arrayOf(
            BitmapFactory.decodeResource(resources, R.drawable.icon_community_grey),
            BitmapFactory.decodeResource(resources, R.drawable.icon_home_grey),
        )
        val tabText = arrayOf("账号","设置")
        binding?.tabBottomBar?.setSelectedIcons(selected.toList())
        binding?.tabBottomBar?.setUnselectIcons(unSelected.toList())
        binding?.tabBottomBar?.setTabText(tabText.toList())
        binding?.tabBottomBar?.setupViewpager(binding?.tabViewPager2)
        binding?.tabBottomBar?.start()
        binding?.tabBottomBar?.registerTabClickListener(object : NavigationBottomBar.OnBottomTabClickListener{
            override fun tabClick(position: Int) {
                Log.d("tab switch", "${position}")
            }
        })

    }

    private fun initPage(){
        val tabPages = mutableListOf<Fragment>()
        tabPages.add(FragHome())
        tabPages.add(FragSetting())
        val pager2Adapter = Pager2Adapter(this)
        pager2Adapter.setData(tabPages)
        binding?.tabViewPager2?.offscreenPageLimit = ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT
        binding?.tabViewPager2?.adapter = pager2Adapter
    }
}
