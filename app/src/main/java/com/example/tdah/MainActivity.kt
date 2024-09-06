package com.example.tdah

import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.view.WindowInsetsController
import android.view.WindowInsets
import android.widget.Button
import android.widget.EditText
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import com.example.tdah.util.DatePickerHelper
import com.example.tdah.util.NavigationUtils
import com.example.tdah.util.Popup.showLoginPopup


class MainActivity : AppCompatActivity() {

    private lateinit var datePickerHelper: DatePickerHelper

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Configura a visualização para lidar com insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Obtém o WindowInsetsController
        val insetsController = window.insetsController

        insetsController?.let {
            // Oculta as barras de navegação e status
            it.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())

            // Torna a ocultação imersiva
            it.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

        val dateInput = findViewById<EditText>(R.id.in_date)
        datePickerHelper = DatePickerHelper(this)

        // Configura o listener para o EditText
        dateInput.setOnClickListener {
            datePickerHelper.showDatePicker(dateInput)
        }

        val buttonLogin: Button = findViewById(R.id.btn_admin)
        buttonLogin.setOnClickListener {
            showLoginPopup(this)
        }
        val buttonStart: Button = findViewById(R.id.btn_start)
        buttonStart.setOnClickListener {
            NavigationUtils.toQuiz(this)
        }
        // Register a callback for the back button
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
            }
        })
    }
}
