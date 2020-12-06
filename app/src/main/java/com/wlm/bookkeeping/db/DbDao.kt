package com.wlm.bookkeeping.db

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Query


@Dao
interface BillDao : BaseDao<Bill> {

    @Query("select * from Bill order by time desc")
    fun getAllBill(): DataSource.Factory<Int, Bill>

    @Query("select * from Bill where time between :start and :end order by time desc")
    fun getGatherBill(start: Long, end: Long): List<Bill>

    @Query("select * from Bill where id = :id")
    fun getBill(id: Int): Bill

    @Query("delete from Bill where id = :id")
    fun delete(id: Int)

    @Query("select sum(price) from Bill where time between :start and :end")
    fun getGatherPrice(start: Long, end: Long): Double
}