package com.hallel.presentation.main

import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.core.view.forEach
import androidx.lifecycle.observe
import com.hallel.domain.event.MenuVO
import com.hallel.presentation.R
import com.hallel.presentation.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {

    private val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buildNavigationMenu()
        initObservers()
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

        main_navigation_menu.setNavigationItemSelectedListener { menuItem ->
            uncheckMenuItens()
            menuItem.isChecked = true
            when (menuItem.itemId) {
                R.string.menu_home -> showToast(getString(menuItem.itemId))
                R.string.menu_schedule -> showToast(getString(menuItem.itemId))
                R.string.menu_my_schedule -> showToast(getString(menuItem.itemId))
                R.string.menu_guests -> showToast(getString(menuItem.itemId))
                R.string.menu_history -> showToast(getString(menuItem.itemId))
                R.string.menu_main_stage -> showToast(getString(menuItem.itemId))
                R.string.menu_map -> showToast(getString(menuItem.itemId))
                R.string.menu_module -> showToast(getString(menuItem.itemId))
                R.string.menu_sponsor -> showToast(getString(menuItem.itemId))
                R.string.menu_rating -> showToast(getString(menuItem.itemId))
                else -> showToast("Nenhum id valido")
            }
            main_drawner_layout.closeDrawer(GravityCompat.START)
            true
        }
    }

    private fun uncheckMenuItens() {
        main_navigation_menu.menu.forEach {
            it.isChecked = false
        }
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
            main_drawner_layout.isDrawerOpen(GravityCompat.START) ->
                main_drawner_layout.closeDrawer(GravityCompat.START)
            else -> main_drawner_layout.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initObservers() {
        viewModel.updateMenuItems().observe(this) { menuList ->
            buildNavigationMenuItems(menuList)
        }
    }

    private fun buildNavigationMenuItems(menuList: List<MenuVO>?) {
        menuList?.let{ menu_list ->
            main_navigation_menu.menu.apply {
                clear()
                menu_list.map{ menuVO ->
                    val res = resources.getIdentifier("menu_${menuVO.resourceName}", "string", packageName)
                    add(0,res,0,getString(res))
                }
            }
        } ?: main_navigation_menu.inflateMenu(R.menu.nav_menu)
    }

    fun getEventMenu() {
        viewModel.onGetEventMenuItems()
    }
}
