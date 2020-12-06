package com.wlm.baselib.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.ItemKeyedDataSource
import androidx.paging.PagedList

abstract class BaseDataSourceFactory<K, T> :
    DataSource.Factory<K, T>() {
    val sourceLivaData = MutableLiveData<ItemKeyedDataSource<K, T>>()

    override fun create(): ItemKeyedDataSource<K, T> {
        val dataSource: ItemKeyedDataSource<K, T> = createDataSource()
        sourceLivaData.postValue(dataSource)
        return dataSource
    }

    abstract fun createDataSource(): ItemKeyedDataSource<K, T>

}

data class Listing<T>(
    val pagedList: LiveData<PagedList<T>>,
    val refresh: () -> Unit //刷新
)