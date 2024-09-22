package com.example.tdah.util

import android.content.Context
import android.content.Intent
import com.example.tdah.ui.DashboardActivity
import com.example.tdah.ui.MainActivity
import com.example.tdah.ui.QuizActivity

/**
 * Utility object for handling navigation between activities.
 */
object NavigationUtils {
    /**
     * Starts the DashboardActivity.
     *
     * @param context The context from which the activity is started.
     */
    fun toDashboard(context: Context) {
        val intent = Intent(context, DashboardActivity::class.java).apply {
            // Add any additional flags or extras here if needed
        }
        context.startActivity(intent)
    }

    /**
     * Starts the QuizActivity.
     *
     * @param context The context from which the activity is started.
     * @param name The user's name.
     * @param email The user's email.
     * @param age The user's age.
     */
    fun toQuiz(context: Context, name: String, email: String,schoolyear:String,phone:Int ,age: String) {
        val intent = Intent(context, QuizActivity::class.java).apply {
            putExtra("USER_NAME", name)
            putExtra("USER_EMAIL", email)
            putExtra("USER_BIRTHDAY", age)
            putExtra("USER_SCHOOLYEAR", schoolyear)
            putExtra("USER_PHONE", phone)
        }
        context.startActivity(intent)
    }

    fun toHome(context: Context) {
        val intent = Intent(context, MainActivity::class.java).apply {
            // Add any additional flags or extras here if needed
        }
        context.startActivity(intent)
    }
}
