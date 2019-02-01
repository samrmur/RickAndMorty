package com.saam.rickandmorty.nav.ui

import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener
import com.saam.rickandmorty.R
import com.saam.rickandmorty.nav.presentation.NavNavigator
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_nav.*
import kotlinx.android.synthetic.main.nav_toolbar.*
import javax.inject.Inject

class NavActivity: AppCompatActivity(), HasSupportFragmentInjector, OnNavigationItemSelectedListener {
    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var navigator: NavNavigator

    private var drawerToggle: ActionBarDrawerToggle? = null
    private var fragTitle: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav)
        setupActionBar()
        setupDrawer()

        if (savedInstanceState == null)
            navigator.toCharacters()
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        drawerToggle?.onConfigurationChanged(newConfig)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_characters -> navigator.toCharacters()
            R.id.nav_locations -> navigator.toLocations()
            R.id.nav_episodes -> navigator.toEpisodes()
        }

        drawer.closeDrawers()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                drawer.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        drawerToggle?.syncState()
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentDispatchingAndroidInjector

    fun setTitle(title: String) {
        supportActionBar?.title = title
        fragTitle = title
    }

    private fun setupActionBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu)
        }
    }

    private fun setupDrawer() {
        navigation_menu.setNavigationItemSelectedListener(this)
        drawerToggle = object : ActionBarDrawerToggle(
            this,
            drawer,
            R.string.drawer_open,
            R.string.drawer_close) {
            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                supportActionBar?.title = getString(R.string.app_name)
                invalidateOptionsMenu()
            }

            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
                supportActionBar?.title = fragTitle
                invalidateOptionsMenu()
            }
        }
    }


}
