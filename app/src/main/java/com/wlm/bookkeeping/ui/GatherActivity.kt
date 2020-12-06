package com.wlm.bookkeeping.ui

import android.app.DatePickerDialog
import com.wlm.baselib.ui.BaseActivity
import com.wlm.bookkeeping.Constant
import com.wlm.bookkeeping.R
import com.wlm.bookkeeping.adapter.GatherBillAdapter
import com.wlm.bookkeeping.format
import kotlinx.android.synthetic.main.activity_gather.*
import java.util.*

class GatherActivity : BaseActivity() {
    override val layoutId: Int = R.layout.activity_gather

    private val adapter by lazy { GatherBillAdapter() }

    private val endCalendar = Calendar.getInstance().apply {
        set(Calendar.SECOND, 59)
        set(Calendar.MINUTE, 59)
        set(Calendar.HOUR_OF_DAY, 23)
    }

    private val startCalendar = Calendar.getInstance().apply {
        set(Calendar.SECOND, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.DAY_OF_YEAR, get(Calendar.DAY_OF_YEAR) - 7)
    }

    private val formatStr by lazy { "yyyy-MM-dd" }

    override fun init() {
        setSupportActionBar(gather_tool)
        gather_tool.setNavigationIcon(R.drawable.arrow_back)
        gather_tool.setTitle(R.string.str_gather)
        gather_tool.setNavigationOnClickListener {
            finish()
        }

        rv_gather.adapter = adapter

        tv_start.text = startCalendar.time.format(formatStr)
        tv_end.text = endCalendar.time.format(formatStr)
        refreshData()

        tv_start.setOnClickListener {
            DatePickerDialog(
                this, DatePickerDialog.THEME_HOLO_LIGHT,
                DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                    startCalendar.set(year, month, dayOfMonth)
                    tv_start.text = startCalendar.time.format(formatStr)
                },
                startCalendar.get(Calendar.YEAR),
                startCalendar.get(Calendar.MONTH),
                startCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
        tv_end.setOnClickListener {
            DatePickerDialog(
                this, DatePickerDialog.THEME_HOLO_LIGHT,
                DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                    endCalendar.set(year, month, dayOfMonth)
                    tv_end.text = endCalendar.time.format(formatStr)
                },
                endCalendar.get(Calendar.YEAR),
                endCalendar.get(Calendar.MONTH),
                endCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        tv_select.setOnClickListener {
            refreshData()
        }


    }

    private fun refreshData() {
        adapter.data.clear()
        adapter.data.addAll(
            Constant.billDao.getGatherBill(
                startCalendar.timeInMillis,
                endCalendar.timeInMillis
            )
        )
        adapter.notifyDataSetChanged()
        tv_gather.text = getString(
            R.string.str_gather_format,
            String.format(
                "%.2f",
                Constant.billDao.getGatherPrice(
                    startCalendar.timeInMillis,
                    endCalendar.timeInMillis
                )
            )
        )

    }

    private var isInit = true
    override fun onResume() {
        super.onResume()
        if (isInit) {
            gather_tool.setTitle(R.string.str_gather)
            isInit = false
        }
    }


}