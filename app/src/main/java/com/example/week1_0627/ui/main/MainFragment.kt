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

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.widget.LinearLayout

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

        // 애니메이션 설정
        val textBox: LinearLayout = view.findViewById(R.id.text_box)

        // 박스를 왼쪽에서 오른쪽으로 슬라이드
        textBox.visibility = View.VISIBLE
        val slideAnimator = ObjectAnimator.ofFloat(textBox, "translationX", -1000f, 0f).apply {
            duration = 1000
        }

        // 박스의 투명도를 0에서 1로 변경
        val textBoxFadeInAnimator = ObjectAnimator.ofFloat(textBox, "alpha", 0f, 1f).apply {
            duration = 500
        }

        // 텍스트가 나타나는 애니메이션
        val textAnimator = ObjectAnimator.ofFloat(textView, "alpha", 0f, 1f).apply {
            duration = 500
            startDelay = 500 // 박스 슬라이드 후에 텍스트가 나타나도록 지연
        }

        // 애니메이션 세트
        val animatorSet = AnimatorSet().apply {
            playTogether(slideAnimator, textBoxFadeInAnimator)
            play(textAnimator).after(slideAnimator)
        }

        // 애니메이션 시작
        animatorSet.start()

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