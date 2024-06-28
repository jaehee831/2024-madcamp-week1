package com.example.week1_0627.ui.dashboard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.week1_0627.R

import android.util.Log
import android.widget.Toast
import java.io.IOException

class ImageViewerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_viewer)

        val folderName = intent.getStringExtra("folderName")
        if (folderName == null) {
            Toast.makeText(this, "폴더 이름을 받아오지 못했습니다.", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val images = getImagesFromFolder(folderName)
        if (images.isEmpty()) {
            Toast.makeText(this, "이미지를 불러오지 못했습니다.", Toast.LENGTH_SHORT).show()
        }

        val recyclerView: RecyclerView = findViewById(R.id.recycler_view_images)
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        recyclerView.adapter = ImageAdapter(images)
    }

    private fun getImagesFromFolder(folderName: String): List<String> {
        val assetManager = assets
        return try {
            assetManager.list(folderName)?.map { "$folderName/$it" } ?: emptyList()
        } catch (e: IOException) {
            Log.e("ImageViewerActivity", "Error loading images from folder: $folderName", e)
            emptyList()
        }
    }
}
