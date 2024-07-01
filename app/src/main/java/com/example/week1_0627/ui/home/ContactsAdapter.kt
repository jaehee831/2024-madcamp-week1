import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.week1_0627.R
import com.example.week1_0627.ui.home.Contact
import android.content.Intent
import android.net.Uri

class ContactsAdapter(
    private val contacts: MutableList<Contact>,
    private val onDeleteClick: (Int) -> Unit,
    private val onFavoriteClick: (Contact) -> Unit
) : RecyclerView.Adapter<ContactsAdapter.ContactViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = contacts[position]
        holder.nameTextView.text = contact.name
        holder.phoneTextView.text = contact.phone
        holder.deleteButton.setOnClickListener {
            onDeleteClick(position)
        }
        holder.favoriteButton.setOnClickListener {
            onFavoriteClick(contact)
        }
        holder.dialButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:${contact.phone}")
            }
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val phoneTextView: TextView = itemView.findViewById(R.id.phoneTextView)
        val deleteButton: Button = itemView.findViewById(R.id.button_delete_contact)
        val favoriteButton: Button = itemView.findViewById(R.id.button_favorite)
        val dialButton: Button = itemView.findViewById(R.id.button_dial_contact)
    }
}
