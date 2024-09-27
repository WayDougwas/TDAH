package com.example.tdah.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.example.tdah.R
import com.example.tdah.util.DateUtils.formatDateInput
import com.example.tdah.util.DateUtils.isValidDate
import com.example.tdah.util.DisplayUtils.setupWindowInsets
import com.example.tdah.util.PopupUtils.showLoginPopup
import com.example.tdah.util.NavigationUtils
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mainView = findViewById<View>(R.id.main)
        val nameInput = findViewById<EditText>(R.id.in_name)
        val emailInput = findViewById<EditText>(R.id.in_email)
        val phoneInput = findViewById<EditText>(R.id.in_phone)
        val dateInput = findViewById<TextInputEditText>(R.id.in_date)

        setupWindowInsets(mainView)

        setupDateInputFormatter(dateInput)

        val serieAutoCompleteTextView: AutoCompleteTextView = findViewById(R.id.in_series)
        val serieOptions = arrayOf("1°", "2°", "3°")
        val serieAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, serieOptions)
        serieAutoCompleteTextView.setAdapter(serieAdapter)
        serieAutoCompleteTextView.setOnClickListener {
            serieAutoCompleteTextView.showDropDown() // Mostra o dropdown ao clicar
        }


        val classAutoCompleteTextView: AutoCompleteTextView = findViewById(R.id.in_class)
        val classOptions = arrayOf("A", "B", "C","D","E","F")
        val classAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, classOptions)
        classAutoCompleteTextView.setAdapter(classAdapter)
        classAutoCompleteTextView.setOnClickListener{
            classAutoCompleteTextView.showDropDown()
        }



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
