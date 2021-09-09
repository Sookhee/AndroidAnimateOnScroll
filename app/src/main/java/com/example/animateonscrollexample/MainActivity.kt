package com.example.animateonscrollexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.animateonscrollexample.databinding.ActivityMainBinding
import com.example.animateonscrollexample.databinding.LayoutHorizontalBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        repeat(10) { // 임시로 layout_horizontal 10개 추가
            binding.layoutContainer.addView(
                LayoutHorizontalBinding.inflate(
                    LayoutInflater.from(this),
                    binding.layoutContainer,
                    false
                ).apply {
                    horizontalRecyclerView.apply {
                        layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                        adapter = HorizontalAdapter().apply {
                            items = listOf("#CAEAF5", "#BDDCF3", "#CFE0FC", "#EED5E4", "#E5D2EE")
                        }
                    }
                }.root
            )
        }
    }
}