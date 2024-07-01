package com.example.week1_0627.ui.dashboard

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.week1_0627.R

import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

class ImageViewerActivity : AppCompatActivity() {

    private lateinit var favoriteImages: MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_viewer)

        // Support Action Bar 설정
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val folderName = intent.getStringExtra("folderName")
        // 상단 액션바 제목을 폴더 이름으로 설정
        supportActionBar?.title = folderName
        favoriteImages = loadFavoriteImages()

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
        recyclerView.adapter = ImageAdapter(images.toMutableList()){ imagePath ->
            toggleFavorite(imagePath)
        }
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    private fun loadFavoriteImages(): MutableList<String> {
        val sharedPreferences = getSharedPreferences("favorites", Context.MODE_PRIVATE)
        val json = sharedPreferences.getString("favorite_images", "[]")
        val type = object : TypeToken<MutableList<String>>() {}.type
        return Gson().fromJson(json, type)
    }

    private fun saveFavoriteImages() {
        val sharedPreferences = getSharedPreferences("favorites", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val json = Gson().toJson(favoriteImages)
        editor.putString("favorite_images", json)
        editor.apply()
    }

    private fun toggleFavorite(imagePath: String) {
        if (favoriteImages.contains(imagePath)) {
            favoriteImages.remove(imagePath)
        } else {
            favoriteImages.add(imagePath)
        }
        saveFavoriteImages()
    }
}
