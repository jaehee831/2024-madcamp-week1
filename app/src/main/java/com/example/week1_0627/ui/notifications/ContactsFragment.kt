package com.example.week1_0627.ui.notifications

import ContactsAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.week1_0627.R

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.week1_0627.ui.home.Contact
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ContactsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var contactsAdapter: ContactsAdapter
    private lateinit var favoriteContacts: MutableList<Contact>
    private lateinit var favoriteImages: MutableList<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_contacts, container, false)
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)

        favoriteContacts = loadFavoriteContacts()

        contactsAdapter = ContactsAdapter(favoriteContacts, {
            // 즐겨찾기 탭에서는 삭제 기능 필요 없음
        }, { contact ->
            // 즐겨찾기 탭에서는 클릭 시 아무 동작하지 않음
        })
        recyclerView.adapter = contactsAdapter

        return view
    }

    private fun loadFavoriteContacts(): MutableList<Contact> {
        val sharedPreferences = context?.getSharedPreferences("favorites", Context.MODE_PRIVATE)
        val json = sharedPreferences?.getString("favorite_contacts", "[]")
        val contactType = object : TypeToken<MutableList<Contact>>() {}.type
        return Gson().fromJson(json, contactType)
    }
}
