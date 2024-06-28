package com.example.week1_0627.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.week1_0627.databinding.FragmentDashboardBinding

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.week1_0627.R
import java.io.File

class DashboardFragment : Fragment() {

    private lateinit var recyclerViewFolders: RecyclerView
    private lateinit var recyclerViewImages: RecyclerView
    private lateinit var folderAdapter: FolderAdapter
    private lateinit var imageAdapter: ImageAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)
        recyclerViewFolders = view.findViewById(R.id.recycler_view_folders)
        recyclerViewFolders.layoutManager = LinearLayoutManager(context)

        recyclerViewImages = view.findViewById(R.id.recycler_view_images)
        recyclerViewImages.layoutManager = GridLayoutManager(context, 3)

        loadFolders()

        return view
    }

    private fun loadFolders() {
        val folders = getFoldersFromAssets(context)
        folderAdapter = FolderAdapter(folders) { folderName ->
            loadImages(folderName)
        }
        recyclerViewFolders.adapter = folderAdapter
    }

    private fun loadImages(folderName: String) {
        val images = getImagesFromFolder(context, folderName)
        imageAdapter = ImageAdapter(images)
        recyclerViewImages.adapter = imageAdapter
    }

    private fun getFoldersFromAssets(context: Context?): List<String> {
        val assetManager = context?.assets
        return assetManager?.list("")?.filter { file ->
            assetManager.list(file)?.isNotEmpty() ?: false
        } ?: emptyList()
    }

    private fun getImagesFromFolder(context: Context?, folderName: String): List<String> {
        val assetManager = context?.assets
        return assetManager?.list(folderName)?.map { "$folderName/$it" } ?: emptyList()
    }
}