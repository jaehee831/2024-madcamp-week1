package com.example.week1_0627.ui.dashboard

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.week1_0627.R

class FolderAdapter(
    private val folders: List<String>,
    private val onFolderClick: (String) -> Unit
) : RecyclerView.Adapter<FolderAdapter.FolderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FolderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_folder, parent, false)
        return FolderViewHolder(view)
    }

    override fun onBindViewHolder(holder: FolderViewHolder, position: Int) {
        val folderName = folders[position]
        holder.folderTextView.text = folderName
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, ImageViewerActivity::class.java).apply {
                putExtra("folderName", folderName)
            }
            context.startActivity(intent)
        }
        holder.folderIcon.setImageResource(R.drawable.ic_folder) // 폴더 아이콘 설정
    }

    override fun getItemCount(): Int = folders.size

    class FolderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val folderTextView: TextView = itemView.findViewById(R.id.folder_name)
        val folderIcon: ImageView = itemView.findViewById(R.id.folder_icon)
    }
}