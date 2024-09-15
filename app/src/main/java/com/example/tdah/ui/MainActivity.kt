package com.example.tdah.ui

import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.Toast
import androidx.activity.viewModels
import com.example.tdah.R
import com.example.tdah.util.DatePickerHelper
import com.example.tdah.util.PopupUtils.showLoginPopup
import com.example.tdah.util.NavigationUtils
import com.example.tdah.viewmodel.UserViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var datePickerHelper: DatePickerHelper
    private val userViewModel: UserViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Handle system bars with insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Hide status and navigation bars
        window.insetsController?.apply {
            hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
            systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

        // Initialize the DatePicker helper and configure date input
        val dateInput = findViewById<EditText>(R.id.in_date)
        datePickerHelper = DatePickerHelper(this)
        dateInput.setOnClickListener {
            datePickerHelper.showDatePicker(dateInput)
        }

        // Capture inputs for user data
        val nameInput = findViewById<EditText>(R.id.in_name)
        val emailInput = findViewById<EditText>(R.id.in_email)

        // Set up login button popup
        val buttonLogin: Button = findViewById(R.id.btn_admin)
        buttonLogin.setOnClickListener {
            showLoginPopup(this)
        }

        // Handle start button click - save user data and navigate to quiz
        val buttonStart: Button = findViewById(R.id.btn_start)
        buttonStart.setOnClickListener {
            val name = nameInput.text.toString()
            val email = emailInput.text.toString()
            val birtday = dateInput.text.toString()

            if (name.isNotEmpty() && email.isNotEmpty() && birtday.isNotEmpty() ) {
                NavigationUtils.toQuiz(this,name,email,birtday,)
            } else {
                Toast.makeText(this, getString(R.string.error_user), Toast.LENGTH_SHORT).show()
            }
        }

        // Handle back button press
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Implement custom logic for the back button if necessary
            }
        })
    }
}
