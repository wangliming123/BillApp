package com.wlm.bookkeeping

import com.wlm.bookkeeping.db.AppDataBase

class Constant {
    companion object {
        const val KEY_BILL = "key_bill"
        const val TIME_FORMAT_STR = "yyyy-MM-dd hh:mm"

        val billDao by lazy { AppDataBase.instance.getBillDao() }
    }
}