package com.zoe.wan.base.adapter

/**
 * ListItem点击事件
 */
abstract class AdapterItemListener<T> {
    abstract fun itemClick(item: T?, position: Int)
}

/**
 * 点击收藏/取消收藏
 */
abstract class AdapterCollectListener<T> {
    abstract fun pay(position: Int, uuid: String)
}
