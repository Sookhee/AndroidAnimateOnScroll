package com.example.animateonscrollexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.animateonscrollexample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        colors.forEach {
            binding.customView.addColorView(it)
        }
    }

    companion object {
        private val colors = listOf(
            "#333333",
            "#444444",
            "#555555",
            "#666666",
            "#777777",
            "#888888",
            "#999999",
            "#AAAAAA",
            "#BBBBBB",
            "#CCCCCC",
            "#DDDDDD",
            "#EEEEEE"
        )
    }
}