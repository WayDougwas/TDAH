package com.example.tdah.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.tdah.R
import com.example.tdah.adapters.UserArrayAdapter
import com.example.tdah.viewmodel.UserViewModel

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ListMenu : Fragment() {
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list_menu, container, false)

        // Initialize the ListView
        val listView: ListView = view.findViewById(R.id.listmenu_list)

        // Set up the adapter (initially empty)
        userViewModel.allUsers.observe(viewLifecycleOwner) { users ->
            // Set up the adapter with the list of users
            val adapter = UserArrayAdapter(requireContext(), users)
            listView.adapter = adapter
        }

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ListMenu().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
