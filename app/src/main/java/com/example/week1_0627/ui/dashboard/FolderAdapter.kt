package com.example.week1_0627.ui.dashboard

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.week1_0627.R
import java.io.IOException

class FolderAdapter(
    private val context: Context,
    private val folders: List<String>,
    private val onFolderClick: (String) -> Unit
) : RecyclerView.Adapter<FolderAdapter.FolderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FolderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_folder, parent, false)
        return FolderViewHolder(view)
    }

    override fun onBindViewHolder(holder: FolderViewHolder, position: Int) {
        val folderName = folders[position]
        holder.folderNameTextView.text = folderName
        val firstImagePath = getFirstImagePath(folderName)
        if (firstImagePath != null) {
            Glide.with(context)
                .load("file:///android_asset/$firstImagePath")
                .into(holder.folderImageView)
        } else {
            holder.folderImageView.setImageResource(R.drawable.ic_folder) // 기본 아이콘 설정
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ImageViewerActivity::class.java).apply {
                putExtra("folderName", folderName)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = folders.size

    private fun getFirstImagePath(folderName: String): String? {
        val assetManager = context.assets
        return try {
            val images = assetManager.list(folderName)
            if (images.isNullOrEmpty()) {
                null
            } else {
                "$folderName/${images[0]}"
            }
        } catch (e: IOException) {
            null
        }
    }

    class FolderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val folderNameTextView: TextView = itemView.findViewById(R.id.folder_name)
        val folderImageView: ImageView = itemView.findViewById(R.id.folder_image)
    }
}