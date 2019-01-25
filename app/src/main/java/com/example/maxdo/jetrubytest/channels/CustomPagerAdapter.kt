package com.example.maxdo.jetrubytest.channels

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.maxdo.jetrubytest.channels.all.AllChannelsFragment
import com.example.maxdo.jetrubytest.channels.favorites.FavChannelsFragment

class CustomPagerAdapter(fm: FragmentManager): FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return listOf(AllChannelsFragment() as Fragment , FavChannelsFragment() as Fragment)[position]
    }

    override fun getCount(): Int {
        return 2
    }


}
