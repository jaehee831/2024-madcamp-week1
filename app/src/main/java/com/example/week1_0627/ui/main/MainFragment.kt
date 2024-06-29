package com.example.week1_0627.ui.main

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.week1_0627.R

class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as? AppCompatActivity)?.supportActionBar?.hide()
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        // 텍스트 설정
        val textView: TextView = view.findViewById(R.id.text_view)
        val fullText = "Discover your ideal fitness coach"
        val spannableString = SpannableString(fullText)
        val start = fullText.indexOf("ideal")
        val end = start + "ideal".length
        val colorSpan = ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.colorAccent)) // #EB5534 색상
        spannableString.setSpan(colorSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        textView.text = spannableString

        return view
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private fun setContentView(activityMain: Int) {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        // 액션바 다시 표시
        (activity as? AppCompatActivity)?.supportActionBar?.show()
    }
}