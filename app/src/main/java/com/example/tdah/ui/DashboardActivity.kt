package com.example.tdah.ui

import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.tdah.databinding.ActivityDashboardBinding
import androidx.activity.OnBackPressedCallback
import com.example.tdah.R
import com.example.tdah.fragments.DashHome
import com.example.tdah.fragments.GraphMenu
import com.example.tdah.fragments.ListMenu
import com.example.tdah.util.DatabaseUltil
import com.example.tdah.util.PopupUtils

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Handle window insets
        ViewCompat.setOnApplyWindowInsetsListener(binding.dashboard) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Configure insets controller
        window.insetsController?.let {
            it.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
            it.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

        // Set up button listeners
        binding.btnDashMenu.setOnClickListener { replaceFragment(DashHome()) }
        binding.btnDashList.setOnClickListener { replaceFragment(ListMenu()) }
        binding.btnGraphMenu.setOnClickListener { replaceFragment(GraphMenu()) }
        binding.btnHomeMenu.setOnClickListener { PopupUtils.showExitConfirmationDialog(this,this) }
        binding.btnSettings.setOnClickListener {  DatabaseUltil.exportDatabase(this)  }

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
