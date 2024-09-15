package com.example.tdah.util

import com.google.android.material.datepicker.MaterialDatePicker
import android.content.Context
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import java.text.SimpleDateFormat
import java.util.*

class DatePickerHelper(private val context: Context) {

    private var datePickerFragment: DialogFragment? = null

    fun showDatePicker(editText: EditText) {
        if (context !is AppCompatActivity) {
            throw IllegalArgumentException("Context must be an instance of AppCompatActivity")
        }

        // Verificar se o seletor de data já está sendo exibido
        if (datePickerFragment != null) {
            return
        }

        val datePicker = MaterialDatePicker.Builder.datePicker()
            .build()

        datePicker.addOnPositiveButtonClickListener { selection ->
            val date = Date(selection)
            val formattedDate = formatDate(date)
            editText.setText(formattedDate)
            datePickerFragment = null
        }

        datePicker.addOnDismissListener {
            datePickerFragment = null
        }

        datePickerFragment = datePicker
        datePicker.show(context.supportFragmentManager, "DATE_PICKER")
    }

    private fun formatDate(date: Date): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return dateFormat.format(date)
    }
}
