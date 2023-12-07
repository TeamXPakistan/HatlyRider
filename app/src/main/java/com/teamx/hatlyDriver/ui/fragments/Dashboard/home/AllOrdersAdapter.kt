package com.teamx.hatlyDriver.ui.fragments.Dashboard.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamx.hatlyDriver.databinding.ItemIncomingListBinding
import com.teamx.hatlyDriver.ui.fragments.chat.socket.model.allmessageData.Doc


class AllOrdersAdapter(val arrayList: ArrayList<Doc>) : RecyclerView.Adapter<AllOrdersAdapter.TopProductViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemTopProductBinding = ItemIncomingListBinding.inflate(inflater, parent, false)
        return TopProductViewHolder(itemTopProductBinding)

    }


    override fun onBindViewHolder(holder: TopProductViewHolder, position: Int) {

        val list: Doc = arrayList[position]

       /* holder.binding.pickup.textView13.text = list.shop.address.googleMapAddress
        holder.binding.pickup.address.text = list.shippingAddress.streat

        holder.binding.pickup.price.text = list.total.toString()+"AED"*/


        holder.itemView.setOnClickListener {}


    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class TopProductViewHolder(itemTopProductBinding: ItemIncomingListBinding) :
        RecyclerView.ViewHolder(itemTopProductBinding.root) {
        val binding = itemTopProductBinding

    }
}