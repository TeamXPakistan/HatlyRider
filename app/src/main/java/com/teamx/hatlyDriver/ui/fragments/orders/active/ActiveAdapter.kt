package com.teamx.hatlyDriver.ui.fragments.orders.active

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.teamx.hatlyDriver.data.dataclasses.pastorder.Doc
import com.teamx.hatlyDriver.databinding.ItemActiveListBinding


class ActiveAdapter(
    val arrayList: ArrayList<Doc>
) : RecyclerView.Adapter<ActiveAdapter.TopProductViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemTopProductBinding = ItemActiveListBinding.inflate(inflater, parent, false)
        return TopProductViewHolder(itemTopProductBinding)

    }


    override fun onBindViewHolder(holder: TopProductViewHolder, position: Int) {

        val list: Doc = arrayList[position]

//        holder.binding.textView13.text = list.pickup.toString()
//        holder.binding.address.text = list.dropOff.toString()


        try {


            holder.binding.textView23.text = list.shop.name
            holder.binding.textView24.text = list.shop.address.streetAddress
            holder.binding.ratingBar.rating = list.shop.ratting.toFloat()
            Picasso.get().load(list.shop.image).into(holder.binding.imgIcon)

            try {
                Picasso.get().load(list.orders.customer.profileImage).into(holder.binding.imgAvatar)

            } catch (e: Exception) {

            }


            Picasso.get().load(list.orders.products[0].image).into(holder.binding.imgIcon)


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
                holder.binding.textView24.text = combinedAddress

            } else {
                println("Street address and/or locality not found in the input string.")
            }



            holder.binding.textView27.text = list.orders.products[0].productName
            holder.binding.textView29.text = list.orders.products[0].prize.toString()

            holder.binding.textView28.text = list.orders.products[0].prize.toString() + " AED"
            holder.binding.textView31.text = list.orders.subTotal.toString()
            holder.binding.textView32.text = list.orders.customer.name
            holder.binding.textView33.text = list.dropOff.address
            holder.binding.textView35.text = list.orders.specialNote
        } catch (e: Exception) {

        }

//        holder.binding.price.text = list.dropOff.toString()+"AED"


        holder.itemView.setOnClickListener {}


    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class TopProductViewHolder(itemTopProductBinding: ItemActiveListBinding) :
        RecyclerView.ViewHolder(itemTopProductBinding.root) {
        val binding = itemTopProductBinding

    }
}