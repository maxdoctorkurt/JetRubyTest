package com.example.maxdo.jetrubytest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.transition.Transition
import androidx.transition.TransitionManager
import com.example.maxdo.jetrubytest.channels.CustomPagerAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*
import androidx.core.view.MenuItemCompat.getActionView
import com.example.maxdo.jetrubytest.channels.searchNews.SearchNewsFragment


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager.adapter = CustomPagerAdapter(supportFragmentManager)
        tabs.setupWithViewPager(viewPager)

        tabs.getTabAt(0)?.setText(R.string.all_channels)
        tabs.getTabAt(1)?.setText(R.string.fav_channels)
        tabs.getTabAt(2)?.setText(R.string.search_news)

        mainToolbar.inflateMenu(R.menu.main_menu_toolbar)

        // initial state
        mainToolbar.findViewById<View>(R.id.toNews).visibility = View.GONE
        mainToolbar.findViewById<View>(R.id.search).visibility = View.GONE

        val menu = mainToolbar.menu

        val myActionMenuItem = menu.findItem(R.id.search)
        val searchView = myActionMenuItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if(query.isNotEmpty())
                SearchNewsFragment.searchOutsidePublisher.onNext(query)
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                if(s.isNotEmpty())
                SearchNewsFragment.searchOutsidePublisher.onNext(s)
                return false
            }
        })

        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {

                tab?.let {

                    when (it.position) {
                        0 -> {

                            TransitionManager.beginDelayedTransition(mainToolbar);

                            mainToolbar.findViewById<View>(R.id.toNews).visibility = View.GONE
                            mainToolbar.findViewById<View>(R.id.search).visibility = View.GONE

                        }
                        1 -> {
                            TransitionManager.beginDelayedTransition(mainToolbar);

                            mainToolbar.findViewById<View>(R.id.toNews).visibility = View.VISIBLE
                            mainToolbar.findViewById<View>(R.id.search).visibility = View.GONE

                        }
                        2 -> {
                            TransitionManager.beginDelayedTransition(mainToolbar);

                            mainToolbar.findViewById<View>(R.id.toNews).visibility = View.GONE
                            mainToolbar.findViewById<View>(R.id.search).visibility = View.VISIBLE
                        }
                        else -> {
                        }
                    }
                }
            }

        })

    }
}
