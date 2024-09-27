package com.example.tdah.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.tdah.R
import com.example.tdah.data.User

class UserArrayAdapter(
    context: Context,
    users: List<User>
) : ArrayAdapter<User>(context, R.layout.list_item_user, users) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.list_item_user, parent, false)

        val user = getItem(position)

        val nameView: TextView = view.findViewById(R.id.user_name)
        val schoolYearView: TextView = view.findViewById(R.id.user_schoolyear)
        val ageView: TextView = view.findViewById(R.id.user_age)
        val birthDayView: TextView = view.findViewById(R.id.user_birthday)
        val phoneView: TextView = view.findViewById(R.id.user_phone)
        val emailView: TextView = view.findViewById(R.id.user_email)
        val resultView: TextView = view.findViewById(R.id.user_result)

        if (user != null) {
            // Use Elvis operator if the value can be null, otherwise, provide the default value directly
            nameView.text = user.name ?: context.getString(R.string.no_name)
            schoolYearView.text = context.getString(R.string.user_schoolyear, user.schoolyerar ?: context.getString(R.string.no_schoolyear))
            phoneView.text = context.getString(R.string.user_phone, user.phone ?: context.getString(R.string.no_phone))
            ageView.text = context.getString(R.string.user_age, user.age ?: 0) // Ensure age is treated as integer
            birthDayView.text = context.getString(R.string.user_birthday, user.birthday ?: context.getString(R.string.unknown))
            emailView.text = context.getString(R.string.user_email, user.email ?: context.getString(R.string.no_email))
            resultView.text = context.getString(R.string.user_result, user.result ?: 0.0) // Ensure result is treated as integer
        } else {
            // Handle the case where user is null
            nameView.text = context.getString(R.string.no_user_data)
        }

        return view
    }
}
