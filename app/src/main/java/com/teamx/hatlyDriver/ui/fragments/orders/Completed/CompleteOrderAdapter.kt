package com.teamx.hatlyDriver.ui.fragments.orders.Completed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamx.hatlyDriver.data.dataclasses.pastorder.Doc
import com.teamx.hatlyDriver.databinding.ItemCompleteOrderBinding
import com.teamx.hatlyDriver.databinding.ItemPastOrderBinding


class CompleteOrderAdapter(val arrayList: ArrayList<Doc>) : RecyclerView.Adapter<CompleteOrderAdapter.TopProductViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemTopProductBinding = ItemCompleteOrderBinding.inflate(inflater, parent, false)
        return TopProductViewHolder(itemTopProductBinding)

    }


    override fun onBindViewHolder(holder: TopProductViewHolder, position: Int) {

        val list: Doc = arrayList[position]

        try {
            holder.binding.address.text = list.dropOff.address
            holder.binding.textView13.text = list.pickup.formattedAddress

            val inputString = list.pickup.formattedAddress

            val streetAddressRegex = Regex("Street Address: (.*?),")
            val localityRegex = Regex("Locality: (.*?),")

            val streetAddressMatch = streetAddressRegex.find(inputString)
            val localityMatch = localityRegex.find(inputString)

            if (streetAddressMatch != null && localityMatch != null) {
                val streetAddress = streetAddressMatch.groupValues[1]
                val locality = localityMatch.groupValues[1]

                // Combine street address and locality into a single string
                val combinedAddress = "$streetAddress, $locality"
                holder.binding.textView13.text = combinedAddress

            } else {
                println("Street address and/or locality not found in the input string.")
            }



            holder.binding.price.text = list.charges.toString() + "AED"
            holder.binding.ordername.text = list.orders.products[0].productName
        }
        catch (e:Exception){

        }

//        holder.binding.price.text = list.dropOff.toString()+"AED"


        holder.itemView.setOnClickListener {}


    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class TopProductViewHolder(itemTopProductBinding: ItemCompleteOrderBinding) :
        RecyclerView.ViewHolder(itemTopProductBinding.root) {
        val binding = itemTopProductBinding

    }
}