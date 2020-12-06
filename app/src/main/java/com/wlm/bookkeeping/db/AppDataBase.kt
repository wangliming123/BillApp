package com.wlm.bookkeeping.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.wlm.baselib.BaseApp

@Database(entities = [Bill::class], version = 1)
abstract class AppDataBase : RoomDatabase() {

    abstract fun getBillDao(): BillDao

    companion object {
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            Room.databaseBuilder(BaseApp.instance, AppDataBase::class.java, "bill.db")
                .allowMainThreadQueries().build()
        }
    }
}