package com.example.week1_0627.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.week1_0627.R

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.week1_0627.ui.dashboard.ImageAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ImagesFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var imageAdapter: ImageAdapter
    private lateinit var favoriteImages: MutableList<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_images, container, false)
        recyclerView = view.findViewById(R.id.recycler_view_images)
        recyclerView.layoutManager = GridLayoutManager(context, 3)

        favoriteImages = loadFavoriteImages()

        imageAdapter = ImageAdapter(favoriteImages) { imagePath ->
            // 즐겨찾기 탭에서는 클릭 시 아무 동작하지 않음
        }
        recyclerView.adapter = imageAdapter

        return view
    }
    private fun loadFavoriteImages(): MutableList<String> {
        val sharedPreferences = context?.getSharedPreferences("favorites", Context.MODE_PRIVATE)
        val json = sharedPreferences?.getString("favorite_images", "[]")
        val type = object : TypeToken<MutableList<String>>() {}.type
        return Gson().fromJson(json, type)
    }
}
