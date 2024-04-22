package com.zoe.wan.android.example.adapter

import android.content.Intent
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.indicator.CircleIndicator
import com.youth.banner.listener.OnBannerListener
import com.youth.banner.listener.OnPageChangeListener
import com.zoe.wan.android.example.R
import com.zoe.wan.android.example.databinding.ItemHomeListBinding
import com.zoe.wan.android.example.repository.data.HomeListItemData
import com.zoe.wan.base.adapter.AdapterCollectListener
import com.zoe.wan.base.adapter.BaseRecyclerAdapter
import com.zoe.wan.base.adapter.BaseViewHolder


class HomeListAdapter : BaseRecyclerAdapter<HomeListItemData?, BaseViewHolder<*>>() {

    companion object {
        //banner item类型
        private const val ItemTypeBannerHeader = 0

        //列表item类型
        private const val ItemTypeList = 1

        //header数量
        private const val HeaderCount = 1
    }

    private var collectListener: AdapterCollectListener<HomeListItemData?>? = null

    fun setCollectListener(listener: AdapterCollectListener<HomeListItemData?>?) {
        this.collectListener = listener
    }

    fun notifyCollectChange(position: Int) {
        if (getDataList()?.isEmpty() == true) {
            return
        }
        getDataList()?.get(position)?.status = -1
        notifyDataSetChanged()
    }

    class MyViewHolder(itemDatabind: ItemHomeListBinding) :
        BaseViewHolder<ItemHomeListBinding>(itemDatabind)

    override fun getItemViewType(position: Int): Int {
        if (HeaderCount != 0 && position < HeaderCount) {
            return ItemTypeBannerHeader
        } else {
            return ItemTypeList
        }
    }

    override fun getViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return MyViewHolder(getBinding<ItemHomeListBinding>(parent, R.layout.item_home_list))
    }

    override fun bindHolder(holder: BaseViewHolder<*>, position: Int) {
        if (holder is MyViewHolder) {
            val item: HomeListItemData? = getDataList()?.get(position)
            holder.binding.item = item
            when (item?.status){
                0 ->{
                    holder.binding.itemHomeTopTag?.text =""
                }
                1 ->{
                    holder.binding.itemHomeTopTag?.text ="待支付"
                }
                -1->{
                    holder.binding.itemHomeTopTag?.text ="支付完毕"
                }
            }
            holder.binding.itemHomeLinear.setOnClickListener {
                if(item?.status !=1){
                    Toast.makeText(it.context, "还没有抢到票", Toast.LENGTH_LONG).show()
                }else{
                    collectListener?.pay(position," ${item.uuid}")

                }
            }
        }
    }
}

