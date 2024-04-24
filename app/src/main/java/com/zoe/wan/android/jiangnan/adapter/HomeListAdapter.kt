package com.zoe.wan.android.jiangnan.adapter

import android.view.ViewGroup
import com.blankj.utilcode.util.ToastUtils
import com.zoe.wan.android.jiangnan.R
import com.zoe.wan.android.jiangnan.databinding.ItemHomeListBinding
import com.zoe.wan.android.jiangnan.repository.data.HomeListItemData
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

    fun notifyCollectChange(position: Int, status: Int = -1) {
        if (getDataList()?.isEmpty() == true) {
            return
        }
        getDataList()?.get(position)?.status = status

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
            when (item?.status) {
                0 -> {
                    holder.binding.payItem?.text = ""
                }

                1 -> {
                    holder.binding.payItem?.text = "待支付"
                }

                -1 -> {
                    holder.binding.payItem?.text = "支付完毕"
                }

                -2 -> {
                    holder.binding.payItem?.text = "已删除"
                }
            }
            holder.binding.payItem.setOnClickListener {
                when (item?.status) {
                    -1 -> {
                        ToastUtils.showLong("已经支付过")
                    }

                    0 -> {
                        ToastUtils.showLong("还没有抢到票")
                    }

                    1 -> {
                        collectListener?.pay(position, "${item.uuid}", "${item.orderStr}")
                    }
                }

            }
            holder.binding.deleteItem.setOnClickListener {
                collectListener?.del(position, "${item?.uuid}")
            }
        }
    }
}

