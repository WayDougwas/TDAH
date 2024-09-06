package com.example.tdah.util

import android.content.Context
import android.content.Intent
import com.example.tdah.DashboardActivity
import com.example.tdah.MainActivity
import com.example.tdah.QuizActivity

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
     */
    fun toQuiz(context: Context) {
        val intent = Intent(context, QuizActivity::class.java).apply {
            // Add any additional flags or extras here if needed
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
