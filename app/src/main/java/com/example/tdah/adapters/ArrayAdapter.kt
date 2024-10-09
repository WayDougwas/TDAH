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
            nameView.text = user.name // Não precisa do Elvis se name é não nulo
            schoolYearView.text = context.getString(R.string.user_schoolyear, user.schoolyear ?: context.getString(R.string.no_schoolyear))
            phoneView.text = context.getString(R.string.user_phone, user.phone) // Não precisa do Elvis se phone é String
            ageView.text = context.getString(R.string.user_age, user.age ?: 0) // Assume 0 se idade for nula
            birthDayView.text = context.getString(R.string.user_birthday, user.birthday)
            emailView.text = context.getString(R.string.user_email, user.email)
            resultView.text = context.getString(R.string.user_result, user.result ?: 0.0) // Use 0.0 se o resultado for nulo
        } else {
            // Handle the case where user is null
            nameView.text = context.getString(R.string.no_user_data)
        }

        return view
    }
}
