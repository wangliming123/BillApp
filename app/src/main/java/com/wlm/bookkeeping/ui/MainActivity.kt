package com.wlm.bookkeeping.ui

import android.Manifest
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import com.wlm.baselib.ui.BaseVMActivity
import com.wlm.bookkeeping.R
import com.wlm.bookkeeping.adapter.BillAdapter
import com.wlm.bookkeeping.startKtxActivity
import com.wlm.bookkeeping.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import pub.devrel.easypermissions.EasyPermissions

class MainActivity : BaseVMActivity<MainViewModel>() {
    override val providerVMClass: Class<MainViewModel> = MainViewModel::class.java
    override val layoutId: Int = R.layout.activity_main


    private val adapter by lazy { BillAdapter() }

    override fun init() {
        super.init()

        setSupportActionBar(main_tool)
        fab_add_bill.setOnClickListener {
            mViewModel.curBill.value = null
            startKtxActivity<BillActivity>()
        }
        rv_bill.adapter = adapter
//        val permissions = arrayOf(
//            Manifest.permission.READ_EXTERNAL_STORAGE,
//            Manifest.permission.WRITE_EXTERNAL_STORAGE
//        )
//        EasyPermissions.requestPermissions(this, "应用需要访问以下权限，请允许", 0, *permissions)
    }


    override fun startObserve() {
        super.startObserve()
        mViewModel.allBill.observe(this, Observer {
            adapter.submitList(it)
        })
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.gather -> {
                startKtxActivity<GatherActivity>()
            }
        }

        return super.onOptionsItemSelected(item)
    }
}