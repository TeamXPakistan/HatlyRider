package com.teamx.hatlyDriver.ui.fragments.Dashboard.notification

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamx.hatlyDriver.databinding.ItemNotificationsBinding
import com.teamx.hatlyDriver.ui.fragments.Dashboard.notification.modelNotification.Doc

class NotificationAdapter(
    private val addressArrayList: ArrayList<Doc>
) : RecyclerView.Adapter<HatlyPopularViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HatlyPopularViewHolder {
        return HatlyPopularViewHolder(
            ItemNotificationsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    }

    override fun onBindViewHolder(holder: HatlyPopularViewHolder, position: Int) {

        val arrayData = addressArrayList[position]

        holder.bind.textView16.text = try {
            arrayData.title
        }catch (e : Exception){
            ""
        }

        holder.bind.textView18.text = try {
            arrayData.description
        }catch (e : Exception){
            ""
        }

        holder.bind.textView17.text = try {
            arrayData.createdAt
        }catch (e:Exception){
            ""
        }

    }

    override fun getItemCount(): Int {
        return addressArrayList.size
    }
}

class HatlyPopularViewHolder(private var binding: ItemNotificationsBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val bind = binding
}