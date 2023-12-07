package com.teamx.hatlyDriver.ui.fragments.wallet

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.teamx.hatlyDriver.databinding.ItemTransactionBinding
import com.teamx.hatlyDriver.utils.TimeFormatter


class TransactionAdapter(
    val arrayList: ArrayList<com.teamx.hatlyDriver.data.dataclasses.transactionHistory.Doc>
) : RecyclerView.Adapter<TransactionAdapter.TopProductViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemTransactionBinding = ItemTransactionBinding.inflate(inflater, parent, false)
        return TopProductViewHolder(itemTransactionBinding)

    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: TopProductViewHolder, position: Int) {

        val list: com.teamx.hatlyDriver.data.dataclasses.transactionHistory.Doc = arrayList[position]

        try {

            holder.binding.tvNoti.text = list.description
            holder.binding.amount.text = list.charges.toString()+" AED"

            holder.binding.time.text= TimeFormatter.formatTimeDifference(list.createdAt)

        }
        catch (e:Exception){

        }

    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class TopProductViewHolder(itemTransactionBinding: ItemTransactionBinding) :
        RecyclerView.ViewHolder(itemTransactionBinding.root) {
        val binding = itemTransactionBinding

    }
}
