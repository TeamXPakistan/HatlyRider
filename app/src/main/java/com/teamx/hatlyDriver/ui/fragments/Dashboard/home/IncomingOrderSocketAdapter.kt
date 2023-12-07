package com.teamx.hatlyDriver.ui.fragments.Dashboard.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamx.hatlyDriver.databinding.ItemIncomingListBinding
import com.teamx.hatlyDriver.ui.fragments.chat.socket.model.incomingOrderSocketData.Doc


class IncomingOrderSocketAdapter(
    val arrayList: ArrayList<Doc>,
    private val onAcceptRejectSocket: onAcceptRejectSocket
) : RecyclerView.Adapter<IncomingOrderSocketAdapter.TopProductViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemTopProductBinding = ItemIncomingListBinding.inflate(inflater, parent, false)
        return TopProductViewHolder(itemTopProductBinding)

    }


    override fun onBindViewHolder(holder: TopProductViewHolder, position: Int) {

        val list: Doc = arrayList[position]

        try {


            val address1 = list.dropOff.address
            val address2 = list.pickup.formattedAddress

            val trimmedAddress1 = address1.substringBefore("\n")
            val trimmedAddress2 = address2.substringBefore("\n")


            holder.binding.pickup.textView13.text = trimmedAddress1
            holder.binding.pickup.address.text = trimmedAddress2
//            holder.binding.ordername.text = list.order.


        /*    holder.binding.pickup.address.text = list.dropOff.address

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
            }
*/


            holder.binding.pickup.price.text = list.total.toString()
            holder.binding.ordername.text = list.orders.products[0].productName
        }
        catch (e:Exception){

        }

        holder.binding.buttons.btnAccept.setOnClickListener {
            onAcceptRejectSocket.onAcceptSokcetClick(position)
        }
        holder.binding.buttons.btnReject.setOnClickListener {
            onAcceptRejectSocket.onRejectSocketClick(position)
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

interface onAcceptRejectSocket{
    fun onAcceptSokcetClick(position: Int)
    fun onRejectSocketClick(position: Int)

}