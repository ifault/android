package com.zoe.wan.android.example.repository.data

data class HomeListItemData(
    val uuid: String,
    val username: String,
    val password: String,
    val details: String?,
    var status: Int?,
    val pay_time: String?,
    var orderStr: String?
)

data class HomeListData(
    val items: List<HomeListItemData?>?,
) {
    override fun toString(): String {
        return "";
    }
}

/**
 * 首页置顶列表数据
 */
class TopHomeListData : ArrayList<HomeListItemData?>()
