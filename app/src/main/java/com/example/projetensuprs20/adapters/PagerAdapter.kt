package com.example.projetensuprs20.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class PagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val fragmentList = ArrayList<Fragment>()

    // fragment du PagerAdapter sélectionné par l'utilisateur
    override fun getItem(position: Int) = fragmentList[position]

    // nombre d item (fragments) dans le PagerAdapter
    override fun getCount()= fragmentList.size

    fun addFragment(fragment: Fragment){
        fragmentList.add(fragment)
    }
}
