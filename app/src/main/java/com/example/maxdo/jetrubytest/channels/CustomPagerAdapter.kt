package com.example.maxdo.jetrubytest.channels

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.maxdo.jetrubytest.channels.all.AllChannelsFragment
import com.example.maxdo.jetrubytest.channels.favorites.FavChannelsFragment
import com.example.maxdo.jetrubytest.channels.searchNews.SearchNewsFragment

class CustomPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val frags = listOf(
        AllChannelsFragment() as Fragment,
        FavChannelsFragment() as Fragment,
        SearchNewsFragment() as Fragment
    )

    override fun getItem(position: Int): Fragment {
        return frags[position]
    }

    override fun getCount(): Int {
        return frags.size
    }


}
