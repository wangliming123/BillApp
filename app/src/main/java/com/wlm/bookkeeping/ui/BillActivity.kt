package com.wlm.bookkeeping.ui

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import com.wlm.baselib.ui.BaseVMActivity
import com.wlm.bookkeeping.Constant
import com.wlm.bookkeeping.R
import com.wlm.bookkeeping.db.Bill
import com.wlm.bookkeeping.format
import com.wlm.bookkeeping.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_bill.*
import java.util.*

class BillActivity : BaseVMActivity<MainViewModel>() {

    override val providerVMClass: Class<MainViewModel> = MainViewModel::class.java
    override val layoutId: Int = R.layout.activity_bill

    override fun init() {
        super.init()

        initView()
    }

    private val calendar = Calendar.getInstance().apply { set(Calendar.SECOND, 0) }

    private fun initView() {
        setSupportActionBar(bill_tool)

        bill_tool.setTitle(R.string.str_create_bill)
        bill_tool.setNavigationIcon(R.drawable.arrow_back)
        bill_tool.setNavigationOnClickListener {
            finish()
        }
        tv_time.text = calendar.time.format()
        tab_time.setOnClickListener {
            showDatePicker()

        }
    }

    private fun showDatePicker() {
        DatePickerDialog(
            this, DatePickerDialog.THEME_HOLO_LIGHT,
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                showTimePicker()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun showTimePicker() {
        TimePickerDialog(
            this, TimePickerDialog.THEME_HOLO_LIGHT,
            TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)
                tv_time.text = calendar.time.format()
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE), true
        ).show()
    }

    private fun initData() {
        val id = intent?.extras?.getInt(Constant.KEY_BILL)
        id?.let {
            val bill = Constant.billDao.getBill(it)
            mViewModel.curBill.value = bill
        }
    }


    override fun startObserve() {
        super.startObserve()


        mViewModel.run {
            curBill.observe(this@BillActivity, Observer {
                calendar.timeInMillis = it.time
                tv_time.text = Date(it.time).format()
                et_price.setText(it.price.toString())
                et_bill_desc.setText(it.desc)
            })
        }

        initData()
    }


    private fun finishBill() {
        if (mViewModel.curBill.value == null) {
            Constant.billDao.insert(
                Bill(0, calendar.timeInMillis, et_bill_desc.text.toString(),
                    et_price.text.toString().run {
                        if (isEmpty()) 0.0 else toDouble()
                    })
            )
        } else {
            mViewModel.curBill.value?.run {
                price = et_price.text.toString().toDouble()
                desc = et_bill_desc.text.toString()
                time = calendar.timeInMillis
                Constant.billDao.update(this)
            }
        }
        finish()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_bill, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.finish -> {
                finishBill()
            }
        }

        return super.onOptionsItemSelected(item)
    }


    private var isInit = true
    override fun onResume() {
        super.onResume()
        if (isInit) {
            bill_tool.setTitle(R.string.str_create_bill)
            isInit = false
        }
    }


}