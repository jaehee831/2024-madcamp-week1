package com.example.week1_0627.ui.home

import ContactsAdapter
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.week1_0627.databinding.FragmentHomeBinding

import android.content.Context
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.week1_0627.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter

import java.io.FileOutputStream
import java.io.InputStream

import androidx.appcompat.widget.SearchView
import android.util.Log // Log 클래스 가져오기

class HomeFragment : Fragment() {

    companion object {
        private const val TAG = "YourActivityOrFragment" // 로그 태그 정의
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var contactsAdapter: ContactsAdapter
    private lateinit var contacts: MutableList<Contact>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)

        // 초기화: assets 폴더에서 contacts.json 파일을 내부 저장소로 복사
        context?.let { copyAssetsToInternalStorage(it, "contacts.json") }

        contacts = loadContactsFromJson().toMutableList()
        contactsAdapter = ContactsAdapter(contacts) { position ->
            deleteContact(position)
        }
        recyclerView.adapter = contactsAdapter

        val addButton: Button = view.findViewById(R.id.button_add_contact)
        addButton.setOnClickListener {
            showAddContactDialog()
        }

        val searchView: SearchView=view.findViewById(R.id.search_view)
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean{
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean{
                contactsAdapter.filter.filter(newText)
                Log.d(TAG, "SearchView Text is changed : $newText")
                return true
            }
        })

        return view
    }

    private fun loadContactsFromJson(): List<Contact> {
        val file = File(context?.filesDir, "contacts.json")
        if (!file.exists()) return emptyList()
        val reader = file.bufferedReader()
        val contactType = object : TypeToken<List<Contact>>() {}.type
        return Gson().fromJson(reader, contactType)
    }

    private fun saveContactsToJson() {
        val jsonString = Gson().toJson(contacts)
        val file = File(context?.filesDir, "contacts.json")
        file.bufferedWriter().use { it.write(jsonString) }
    }

    private fun showAddContactDialog() {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_add_contact, null)
        val nameEditText: EditText = dialogView.findViewById(R.id.edit_text_name)
        val phoneEditText: EditText = dialogView.findViewById(R.id.edit_text_phone)
        val saveButton: Button = dialogView.findViewById(R.id.button_save)

        val dialog = AlertDialog.Builder(context)
            .setView(dialogView)
            .create()

        saveButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val phone = phoneEditText.text.toString()
            if (name.isNotEmpty() && phone.isNotEmpty()) {
                val newContact = Contact(name, phone)
                contacts.add(newContact)
                contactsAdapter.notifyItemInserted(contacts.size - 1)
                saveContactsToJson()
                dialog.dismiss()
            }
        }

        dialog.show()
    }

    private fun copyAssetsToInternalStorage(context: Context, fileName: String) { //파일 복사 메서드 추가
        val file = File(context.filesDir, fileName)
        if (!file.exists()) {
            val assetManager = context.assets
            val inputStream: InputStream = assetManager.open(fileName)
            val outputStream: FileOutputStream = FileOutputStream(file)
            val buffer = ByteArray(1024)
            var length: Int
            while (inputStream.read(buffer).also { length = it } > 0) {
                outputStream.write(buffer, 0, length)
            }
            inputStream.close()
            outputStream.close()
        }
    }

    private fun deleteContact(position: Int) {
        contacts.removeAt(position)
        contactsAdapter.notifyItemRemoved(position)
        saveContactsToJson()
    }
}
