package com.example.tdah.ui

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import com.example.tdah.R
import com.example.tdah.util.DateUtils.formatDateInput
import com.example.tdah.util.DateUtils.isValidDate
import com.example.tdah.util.PopupUtils.showLoginPopup
import com.example.tdah.util.NavigationUtils
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val nameInput = findViewById<EditText>(R.id.in_name)
        val emailInput = findViewById<EditText>(R.id.in_email)
        val phoneInput = findViewById<EditText>(R.id.in_phone)
        val dateInput = findViewById<TextInputEditText>(R.id.in_date)

        setupWindowInsets()

        setupDateInputFormatter(dateInput)

        val buttonLogin: Button = findViewById(R.id.btn_admin)
        buttonLogin.setOnClickListener {
            showLoginPopup(this)
        }

        val buttonStart: Button = findViewById(R.id.btn_start)
        buttonStart.setOnClickListener {
            handleStartButtonClick(nameInput, emailInput, phoneInput, dateInput)
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Implement custom logic for the back button if necessary
            }
        })
    }

    private fun setupWindowInsets() {
        // API 30+ usa WindowInsetsController
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }

            window.insetsController?.apply {
                hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            // Para API < 30, usa flags de LayoutParams
            @Suppress("DEPRECATION")
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }

    private fun setupDateInputFormatter(dateInput: TextInputEditText) {
        dateInput.addTextChangedListener(object : TextWatcher {
            private var isFormatting = false

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!isFormatting) {
                    isFormatting = true
                    val formattedDate = formatDateInput(s.toString())
                    dateInput.setText(formattedDate)
                    dateInput.setSelection(formattedDate.length)
                    isFormatting = false
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun handleStartButtonClick(
        nameInput: EditText,
        emailInput: EditText,
        phoneInput: EditText,
        dateInput: TextInputEditText
    ) {
        val name = nameInput.text.toString()
        val email = emailInput.text.toString()
        val phone = phoneInput.text.toString().toIntOrNull()
        val birthday = dateInput.text.toString()

        if (name.isNotEmpty() && email.isNotEmpty() && birthday.isNotEmpty()) {
            if (isValidDate(birthday)) {
                if (phone != null) {
                    NavigationUtils.toQuiz(this, name, email, "2B", phone, birthday)
                } else {
                    Toast.makeText(this, getString(R.string.error_phone_invalid), Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, getString(R.string.error_date_invalid), Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, getString(R.string.error_user), Toast.LENGTH_SHORT).show()
        }
    }
}
