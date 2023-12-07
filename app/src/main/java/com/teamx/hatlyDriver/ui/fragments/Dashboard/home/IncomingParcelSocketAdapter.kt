package com.teamx.hatlyDriver.ui.fragments.Dashboard.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamx.hatlyDriver.databinding.ItemIncomingListBinding
import com.teamx.hatlyDriver.ui.fragments.chat.socket.model.incomingParcelSoocketData.Doc


class IncomingParcelSocketAdapter(val arrayList: ArrayList<Doc>,
                                  private val onAcceptRejectParcel: onAcceptRejectParcel) : RecyclerView.Adapter<IncomingParcelSocketAdapter.TopProductViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemTopProductBinding = ItemIncomingListBinding.inflate(inflater, parent, false)
        return TopProductViewHolder(itemTopProductBinding)

    }


    override fun onBindViewHolder(holder: TopProductViewHolder, position: Int) {

        val list: Doc = arrayList[position]

        try {




            val address1 = list.parcel.receiverLocation.location.address
            val address2 = list.parcel.senderLocation.location.address

            val trimmedAddress1 = address1.substringBefore("\n")
            val trimmedAddress2 = address2.substringBefore("\n")


            holder.binding.pickup.textView13.text = trimmedAddress1
            holder.binding.pickup.address.text = trimmedAddress2


//            holder.binding.pickup.address.text = list.parcel.senderLocation.location.address

//            val inputString = list.parcel.senderLocation.location.address
//
//            val streetAddressRegex = Regex("Street Address: (.*?),")
//            val localityRegex = Regex("Locality: (.*?),")
//
//            val streetAddressMatch = streetAddressRegex.find(inputString)
//            val localityMatch = localityRegex.find(inputString)
//
//            if (streetAddressMatch != null && localityMatch != null) {
//                val streetAddress = streetAddressMatch.groupValues[1]
//                val locality = localityMatch.groupValues[1]
//
//                // Combine street address and locality into a single string
//                val combinedAddress = "$streetAddress, $locality"
//                holder.binding.pickup.textView13.text = combinedAddress
//
//            } else {
//                println("Street address and/or locality not found in the input string.")
//            }



            holder.binding.pickup.price.text = list.parcel.fare.toString()
        }
        catch (e:Exception){

        }

        holder.binding.buttons.btnAccept.setOnClickListener {
            onAcceptRejectParcel.onAcceptParcelClick(position)
        }
        holder.binding.buttons.btnReject.setOnClickListener {
            onAcceptRejectParcel.onRejectParcelClick(position)
        }

    }

    override fun getItemCount(): Int {
        Log.d("TAG", "getItemCount1212: ${arrayList.size}")
        return arrayList.size
    }

    class TopProductViewHolder(itemTopProductBinding: ItemIncomingListBinding) :
        RecyclerView.ViewHolder(itemTopProductBinding.root) {
        val binding = itemTopProductBinding

    }
}

interface onAcceptRejectParcel{
    fun onAcceptParcelClick(position: Int)
    fun onRejectParcelClick(position: Int)

}
