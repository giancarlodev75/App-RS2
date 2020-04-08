package com.example.projetensuprs20.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.viewpager.widget.ViewPager
import com.example.projetensuprs20.adapters.PagerAdapter
import com.example.projetensuprs20.fragments.ChatFragment
import com.example.projetensuprs20.fragments.ContactsFragment
import com.example.projetensuprs20.fragments.TimelineFragment
import com.example.projetf2.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance()}
    private var prevBottomSelected : MenuItem? = null

    private lateinit var adapter: PagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpViewPager(getPagerAdapter())
        setUpBottomNavigationBar()
    }

    // Méthode pour indiquer le PagerAdapter utilisé
    private fun getPagerAdapter() : PagerAdapter {

        // Utilisation du FragmentManager pour ajout des Fragments

        //  PagerAdapter(supportFragmentManager).addFragment(Nom du Fragment)
        // Optimisation du code avec adapter =
        adapter = PagerAdapter(supportFragmentManager)

        // On va ajouter les Fragments dans le PagerAdapter

        adapter.addFragment(ContactsFragment())
        adapter.addFragment(ChatFragment())
        adapter.addFragment(TimelineFragment())
        return adapter

    }

    // La page du PagerAdapter à afficher = le fragment
    private fun setUpViewPager(adapter: PagerAdapter){
        viewPager.adapter = adapter

        // Afficher le Fragment en fonction du scroll sur le PagerAdapter ou du clic sur l'icone du fragment
        viewPager.addOnPageChangeListener(object: ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageSelected(position: Int){

                if(prevBottomSelected == null) {
                    bottomNavigation.menu.getItem(0).isChecked= false
                } else {
                    prevBottomSelected!!.isChecked = false
                }
                bottomNavigation.menu.getItem(position).isChecked = true
                prevBottomSelected = bottomNavigation.menu.getItem(position)

            }
        })
    }

    // La bottom nav
    private fun setUpBottomNavigationBar(){
        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when(item.itemId){
                R.id.bottom_nav_contacts -> {
                    viewPager.currentItem = 0; true
                }
                R.id.bottom_nav_chat -> {
                    viewPager.currentItem = 1; true
                }
                R.id.bottom_nav_timeline -> {
                    viewPager.currentItem = 2; true
                }
                else -> false
            }
        }
    }

}
