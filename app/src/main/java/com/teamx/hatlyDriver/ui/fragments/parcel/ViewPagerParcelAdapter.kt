package com.teamx.hatlyDriver.ui.fragments.parcel

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.teamx.hatlyDriver.ui.fragments.parcel.Completed.CompletedParcelFragment
import com.teamx.hatlyDriver.ui.fragments.parcel.activeParcel.ActiveParcelFragment
import com.teamx.hatlyDriver.ui.fragments.parcel.incomingParcel.IncomingParcelFragment

class ViewPagerParcelAdapter(fragmentActivity: FragmentActivity, private var totalCount: Int) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return totalCount
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ActiveParcelFragment()
            1 -> IncomingParcelFragment()
            2 -> CompletedParcelFragment()
            else -> ActiveParcelFragment()
        }
    }

}