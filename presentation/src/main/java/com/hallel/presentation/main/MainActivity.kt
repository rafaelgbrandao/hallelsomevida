package com.hallel.presentation.main

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.hallel.presentation.R
import com.hallel.presentation.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buildNavigationMenu()
    }

    private fun buildNavigationMenu() {
        val toggle = ActionBarDrawerToggle(
            this,
            main_drawner_layout,
            R.string.open_drawer,
            R.string.close_drawer
        )
        main_drawner_layout.addDrawerListener(toggle)
        toggle.syncState()

        main_navigation_menu.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {

        main_drawner_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (main_drawner_layout.isDrawerOpen(GravityCompat.START)) {
            main_drawner_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when {
            main_drawner_layout.isDrawerOpen(GravityCompat.START) -> {
                main_drawner_layout.closeDrawer(GravityCompat.START)
            }
            else -> main_drawner_layout.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }
}
