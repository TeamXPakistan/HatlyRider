package com.teamx.hatlyDriver.ui.fragments.parcel.activeParcel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.teamx.hatlyDriver.data.dataclasses.pastParcels.Doc
import com.teamx.hatlyDriver.databinding.ItemActiveParcelListBinding


class ActiveParcelAdapter(
    val arrayList: ArrayList<Doc>
) : RecyclerView.Adapter<ActiveParcelAdapter.TopProductViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemTopProductBinding = ItemActiveParcelListBinding.inflate(inflater, parent, false)
        return TopProductViewHolder(itemTopProductBinding)

    }


    override fun onBindViewHolder(holder: TopProductViewHolder, position: Int) {

        val list: Doc = arrayList[position]

//        holder.binding.textView13.text = list.pickup.toString()
//        holder.binding.address.text = list.dropOff.toString()


        try {

/*
            holder.binding.textView23.text = list.shop.name
            holder.binding.textView24.text = list.shop.address.streetAddress
            holder.binding.ratingBar.rating = list.shop.ratting.toFloat()
            Picasso.get().load(list.shop.image).into(holder.binding.imgIcon)*/

            try {
                Picasso.get().load(list.parcel.senderId.profileImage).into(holder.binding.imgAvatar)

            } catch (e: Exception) {

            }

//            Picasso.get().load(list.parcel.details.image).into(holder.binding.imgIcon)


            val address = list.parcel.receiverLocation.location.address

            val trimmedAddress = address.substringBefore("\n")
            holder.binding.textView33.text = trimmedAddress


            holder.binding.textView27.text = list.parcel.details.item
            holder.binding.textView31.text = list.parcel.fare.toString()
            holder.binding.textView32.text = list.parcel.senderId.name
//            holder.binding.textView35.text = list.orders.specialNote
        } catch (e: Exception) {

        }

//        holder.binding.price.text = list.dropOff.toString()+"AED"


        holder.itemView.setOnClickListener {}


    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class TopProductViewHolder(itemTopProductBinding: ItemActiveParcelListBinding) :
        RecyclerView.ViewHolder(itemTopProductBinding.root) {
        val binding = itemTopProductBinding

    }
}