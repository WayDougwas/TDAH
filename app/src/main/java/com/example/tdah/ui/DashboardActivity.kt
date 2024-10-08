package com.example.tdah.ui

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.tdah.R
import com.example.tdah.databinding.ActivityDashboardBinding
import com.example.tdah.fragments.DashHome
import com.example.tdah.fragments.GraphMenu
import com.example.tdah.fragments.ListMenu
import com.example.tdah.fragments.Settings
import com.example.tdah.util.DisplayUtils.setupWindowInsets
import com.example.tdah.util.PopupUtils

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mainView = findViewById<View>(R.id.dashboard)
        setupWindowInsets(mainView)

        // Set up button listeners
        binding.btnDashMenu.setOnClickListener { replaceFragment(DashHome()) }
        binding.btnDashList.setOnClickListener { replaceFragment(ListMenu()) }
        binding.btnGraphMenu.setOnClickListener { replaceFragment(GraphMenu()) }
        binding.btnHomeMenu.setOnClickListener { PopupUtils.showExitConfirmationDialog(this) }
        binding.btnSettings.setOnClickListener { replaceFragment(Settings()) }

        // Set initial fragment
        if (savedInstanceState == null) {
            replaceFragment(DashHome())
        }


        // Register a callback for the back button
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
            }
        })
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frg_con_dash, fragment)
            addToBackStack(null)  // Optional if you want to add to back stack
            commit()
        }
    }
}
