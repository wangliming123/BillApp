package com.wlm.bookkeeping.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Bill(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var time: Long,
    var desc: String,
    var price: Double
)