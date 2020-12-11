package com.wlm.bookkeeping.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.wlm.bookkeeping.Constant
import com.wlm.bookkeeping.R
import com.wlm.bookkeeping.db.Bill
import com.wlm.bookkeeping.format
import com.wlm.bookkeeping.startKtxActivity
import com.wlm.bookkeeping.ui.BillActivity

class BillAdapter : PagedListAdapter<Bill, BillViewHolder>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BillViewHolder =
        BillViewHolder(parent, true)

    override fun onBindViewHolder(holder: BillViewHolder, position: Int) {

        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            it.context.startKtxActivity<BillActivity>(value = Constant.KEY_BILL to getItem(position)!!.id)
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Bill>() {
            override fun areItemsTheSame(oldItem: Bill, newItem: Bill): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Bill, newItem: Bill): Boolean =
                oldItem == newItem

        }
    }
}

class GatherBillAdapter : RecyclerView.Adapter<BillViewHolder>() {

    val data = mutableListOf<Bill>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        BillViewHolder(parent, false)

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: BillViewHolder, position: Int) {
        holder.bind(data[position])
    }

}

class BillViewHolder(parent: ViewGroup, deleteVisibility: Boolean) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_bill, parent, false)
) {

    private val tvPrice = itemView.findViewById<TextView>(R.id.tv_bill_price)
    private val tvTime = itemView.findViewById<TextView>(R.id.tv_bill_time)
    private val tvDesc = itemView.findViewById<TextView>(R.id.tv_bill_desc)
    private val tvDelete = itemView.findViewById<TextView>(R.id.tv_bill_delete)

    init {
        tvDelete.visibility = if (deleteVisibility) View.VISIBLE else View.GONE
    }

    fun bind(bill: Bill?) {
        bill?.run {
            tvPrice.text = String.format("%.2f", price)
            tvTime.text = time.format()
            tvDesc.text = desc
            tvDelete.setOnClickListener {
                Constant.billDao.delete(this)
            }
        }
    }

}