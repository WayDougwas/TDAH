package com.example.tdah.util

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.example.tdah.R
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period
import java.util.Calendar
import java.util.Date
import java.util.Locale

object DateUtils {

    private fun formatDate(date: Date): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return dateFormat.format(date)
    }


    fun formatDateInput(date: String): String {
        val cleaned = date.replace("/", "")
        val formatted = StringBuilder()

        for (i in cleaned.indices) {
            if (i == 2 || i == 4) {
                formatted.append("/")
            }
            formatted.append(cleaned[i])
        }

        return formatted.toString()
    }

    fun isValidDate(date: String): Boolean {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        dateFormat.isLenient = false

        return try {
            val parsedDate = dateFormat.parse(date)
            val today = Calendar.getInstance().time
            parsedDate != null && !parsedDate.after(today)
        } catch (e: Exception) {
            false
        }
    }

    fun calculateAge(birthDate: LocalDate, currentDate: LocalDate = LocalDate.now()): Int {
        require(birthDate <= currentDate) { "Birth date must not be in the future." }
        return Period.between(birthDate, currentDate).years
    }
}
