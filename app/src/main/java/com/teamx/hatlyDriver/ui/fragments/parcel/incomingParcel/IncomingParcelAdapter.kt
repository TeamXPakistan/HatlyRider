package com.teamx.hatlyDriver.ui.fragments.parcel.incomingParcel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamx.hatlyDriver.data.dataclasses.pastParcels.Doc
import com.teamx.hatlyDriver.databinding.ItemIncomingListBinding


class IncomingParcelAdapter(
    val arrayList: ArrayList<Doc>,
    private val onAccepetReject: onAcceptRejectPar
) : RecyclerView.Adapter<IncomingParcelAdapter.TopProductViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemTopProductBinding = ItemIncomingListBinding.inflate(inflater, parent, false)
        return TopProductViewHolder(itemTopProductBinding)

    }


    override fun onBindViewHolder(holder: TopProductViewHolder, position: Int) {

        val list: Doc = arrayList[position]

        try {

            val pickup = list.parcel.senderLocation.location.address
            val dropoff = list.parcel.receiverLocation.location.address

            val trimmedPickup = pickup.substringBefore("\n")
            val trimmedDrop = dropoff.substringBefore("\n")
            holder.binding.pickup.address.text = trimmedPickup
            holder.binding.pickup.textView13.text = trimmedDrop

            holder.binding.ordername.text = list.parcel.details.item

            /*
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
                            holder.binding.pickup.textView13.text = combinedAddress

                        } else {
                            println("Street address and/or locality not found in the input string.")
                        }*/



            holder.binding.pickup.price.text = list.parcel.fare.toString()+" AED"
        }
        catch (e:Exception){

        }

        holder.binding.buttons.btnAccept.setOnClickListener {
            onAccepetReject.onAcceptClick(position)
        }
        holder.binding.buttons.btnReject.setOnClickListener {
            onAccepetReject.onRejectClick(position)
        }

    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class TopProductViewHolder(itemTopProductBinding: ItemIncomingListBinding) :
        RecyclerView.ViewHolder(itemTopProductBinding.root) {
        val binding = itemTopProductBinding

    }
}

interface onAcceptRejectPar{
    fun onAcceptClick(position: Int)
    fun onRejectClick(position: Int)

}