package com.esgi.yfitops

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    private lateinit var navBar: BottomNavigationView
    private lateinit var hostView: FragmentContainerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navBar = findViewById(R.id.nav_bar)
        hostView = findViewById(R.id.host_view)
        val navHost = supportFragmentManager.findFragmentById(R.id.host_view) as NavHostFragment
        setupWithNavController(navBar, navHost.navController)
    }

}