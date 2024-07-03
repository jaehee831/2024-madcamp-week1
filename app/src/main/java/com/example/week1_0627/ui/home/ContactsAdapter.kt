//package com.example.week1_0627.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.week1_0627.R
import com.example.week1_0627.ui.home.Contact
<<<<<<< Updated upstream

class ContactsAdapter(
    private val contacts: MutableList<Contact>,
    private val onDeleteClick: (Int) -> Unit
) : RecyclerView.Adapter<ContactsAdapter.ContactViewHolder>() {
=======
import android.content.Intent
import android.net.Uri
import android.widget.Filter
import android.widget.Filterable

class ContactsAdapter(
    private val contacts: MutableList<Contact>,
    private val onDeleteClick: (Int) -> Unit,
    private val onFavoriteClick: (Contact) -> Unit
) : RecyclerView.Adapter<ContactsAdapter.ContactViewHolder>(), Filterable {

    private var filteredContacts: MutableList<Contact> = contacts.toMutableList()
>>>>>>> Stashed changes

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contact_item, parent, false)
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = contacts[position]
        holder.nameTextView.text = contact.name
        holder.phoneTextView.text = contact.phone
        holder.deleteButton.setOnClickListener {
            onDeleteClick(position)
        }
    }

    override fun getItemCount(): Int {
        return contacts.size
        //return filteredContacts.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val query=constraint?.toString()?.lowercase() ?: ""
                val results=FilterResults()
                results.values=if (query.isEmpty()) {
                    contacts
                } else{
                    contacts.filter {
                        it.name.lowercase().contains(query) || it.phone.contains(query)
                    }.toMutableList()
                }
                return results
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredContacts=results?.values as? MutableList<Contact> ?: mutableListOf()
                notifyDataSetChanged()
            }
        }
    }

    class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val phoneTextView: TextView = itemView.findViewById(R.id.phoneTextView)
        val deleteButton: Button = itemView.findViewById(R.id.button_delete_contact)
    }
}
