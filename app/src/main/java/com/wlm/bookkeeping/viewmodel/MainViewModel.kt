package com.wlm.bookkeeping.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.wlm.baselib.common.BaseViewModel
import com.wlm.bookkeeping.Constant
import com.wlm.bookkeeping.db.Bill

class MainViewModel : BaseViewModel() {
    companion object {
        private const val PAGE_SIZE = 10
        private const val ENABLE_PLACEHOLDERS = false
    }

    val allBill = LivePagedListBuilder(
        Constant.billDao.getAllBill(), PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .setEnablePlaceholders(ENABLE_PLACEHOLDERS)
            .setInitialLoadSizeHint(PAGE_SIZE * 2)
            .build()
    ).build()

    val curBill = MutableLiveData<Bill>()


}