package com.teamx.hatlyDriver.ui.fragments.chat.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamx.hatlyDriver.data.models.riderMessage.RiderMessage
import com.teamx.hatlyDriver.databinding.ItemChatRiderBinding
import com.teamx.hatlyDriver.databinding.ItemChatUserBinding
import timber.log.Timber


class ChatAdapter(
    private val messageArrayList: ArrayList<RiderMessage>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_USER = 1
    private val VIEW_TYPE_Rider = 2


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            VIEW_TYPE_Rider -> {
                MessageRiderViewHolder(
                    ItemChatRiderBinding.inflate(
                        LayoutInflater.from(viewGroup.context), viewGroup, false
                    )
                )
            }

            VIEW_TYPE_USER -> {
                MessageUserViewHolder(
                    ItemChatUserBinding.inflate(
                        LayoutInflater.from(viewGroup.context), viewGroup, false
                    )
                )
            }

            else -> throw IllegalArgumentException("Invalid item type")
        }
    }


    override fun getItemCount(): Int {
        return messageArrayList.size
    }


    override fun getItemViewType(position: Int): Int {
        return when (messageArrayList[position].isUser) {
            true -> VIEW_TYPE_USER
            false -> VIEW_TYPE_Rider
        }
    }

    private var todayTimeString = ""


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val messagesUser = messageArrayList[position]


        holder.itemViewType

        when (messagesUser.isUser) {
            true -> {



                (holder as MessageUserViewHolder).bind.txtMessage.text = messagesUser.message
                (holder as MessageUserViewHolder).bind.todayTime.text = messagesUser.createdAt

//                if (messagesUser.imgMsg.isNullOrBlank()) {
//
//                    (holder as MessageUserViewHolder).bind.txtMessage.visibility = View.GONE
////                    Picasso.get().load(messagesUser.imgUrl).into(holder.bind.imgMessage)
//                } else {
//                    Picasso.get().load(messagesUser.imgMsg).into((holder as MessageUserViewHolder).bind.txtMessage)
//                    (holder as MessageUserViewHolder).bind.txtMessage.visibility = View.VISIBLE
//                }

//                if (messagesUser.profileImg.isNullOrBlank()) {
//
//                    (holder as MessageUserViewHolder).bind.imgUser.visibility = View.GONE
////                    Picasso.get().load(messagesUser.imgUrl).into(holder.bind.imgMessage)
//                } else {
//                    Picasso.get().load(messagesUser.profileImg)
//                        .into((holder as MessageUserViewHolder).bind.imgUser)
//                    (holder as MessageUserViewHolder).bind.imgUser.visibility = View.VISIBLE
//                }

                (holder as MessageUserViewHolder).bind.todayTime.text = messagesUser.createdAt

                Timber.tag("TAG").d( "onBindViewHolder: true")

//                if (todayTimeString == messagesUser.timeAndDate) {
//                    (holder as MessageUserViewHolder).bind.todayTime.visibility = View.GONE

//                } else {
//                    (holder as MessageUserViewHolder).bind.todayTime.visibility = View.VISIBLE

//                }

                todayTimeString = messagesUser.createdAt
            }

            false -> {


                val text: String = messagesUser.message

                Timber.tag("TAG").d( "onBindViewHolder: false")

//                (holder as MessageRiderViewHolder).bind.todayTime1.text = messagesUser.timeAndDate

                Timber.tag("TAG").d( "same $text $position ${messagesUser.createdAt} $todayTimeString")

                if (messagesUser.createdAt == todayTimeString) {
                    (holder as MessageRiderViewHolder).bind.todayTime.visibility = View.GONE
                } else {
                    (holder as MessageRiderViewHolder).bind.todayTime.visibility = View.VISIBLE
                }


                (holder as MessageRiderViewHolder).bind.txtMessage.text = text
                (holder as MessageRiderViewHolder).bind.todayTime.text = messagesUser.createdAt
//                (holder as MessageRiderViewHolder).bind.txtMessageTime.text = messagesUser.timeAndDate
//                if (messagesUser.profileImg.isNullOrBlank()) {
//
//                    (holder as MessageRiderViewHolder).bind.imgUser.visibility = View.GONE
////                    Picasso.get().load(messagesUser.imgUrl).into(holder.bind.imgMessage)
//                } else {
//                    Picasso.get().load(messagesUser.profileImg)
//                        .into((holder as MessageRiderViewHolder).bind.imgUser)
//                    (holder as MessageRiderViewHolder).bind.imgUser.visibility = View.VISIBLE
//                }
//                if (messagesUser.imgMsg.isNullOrBlank()) {
//
//                    (holder as MessageRiderViewHolder).bind.imgMessage.visibility = View.GONE
////                    Picasso.get().load(messagesUser.imgUrl).into(holder.bind.imgMessage)
//                } else {
//                    Picasso.get().load(messagesUser.imgMsg)
//                        .into((holder as MessageRiderViewHolder).bind.imgMessage)
//                    (holder as MessageRiderViewHolder).bind.imgMessage.visibility = View.VISIBLE
//                }

                todayTimeString = messagesUser.createdAt
                Timber.tag("TAG").d( "working")
            }
//            else -> throw IllegalArgumentException("Invalid item type")
        }

    }
}


class MessageRiderViewHolder(binding: ItemChatRiderBinding) :
    RecyclerView.ViewHolder(binding.root) {

    val bind = binding

}

class MessageUserViewHolder(binding: ItemChatUserBinding) :
    RecyclerView.ViewHolder(binding.root) {

    val bind = binding

}
