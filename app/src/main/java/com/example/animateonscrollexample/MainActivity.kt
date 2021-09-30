package com.example.animateonscrollexample

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.ORIENTATION_HORIZONTAL
import com.example.animateonscrollexample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val wordList = mutableListOf("TAB1", "TAB2", "TAB3", "TAB4", "TAB5")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        // 스크롤 인터렉션 세팅
        binding.viewPagerWrapper.customAlpha = 0.0f

        binding.indicatorWrapper.customAlpha = 0.0f
        binding.indicatorWrapper.delay = 250

        binding.subContentsWrapper.customAlpha = 0.0f
        binding.subContentsWrapper.delay = 500

        setViewPager()
        setIndicator()

        binding.recyclerView1.adapter = HorizontalAdapter().apply {
            items = listOf("#CAEAF5", "#BDDCF3", "#CFE0FC", "#EED5E4", "#E5D2EE")
        }

        binding.recyclerView2.adapter = HorizontalAdapter().apply {
            items = listOf("#CAEAF5", "#BDDCF3", "#CFE0FC", "#EED5E4", "#E5D2EE")
        }

        binding.recyclerView3.adapter = HorizontalAdapter().apply {
            items = listOf("#CAEAF5", "#BDDCF3", "#CFE0FC", "#EED5E4", "#E5D2EE")
        }

        binding.recyclerView4.adapter = HorizontalAdapter().apply {
            items = listOf("#CAEAF5", "#BDDCF3", "#CFE0FC", "#EED5E4", "#E5D2EE")
        }

        binding.recyclerView5.adapter = HorizontalAdapter().apply {
            items = listOf("#CAEAF5", "#BDDCF3", "#CFE0FC", "#EED5E4", "#E5D2EE")
        }
    }

    private fun setViewPager() {
        binding.viewPager.apply {
            adapter = ViewPagerAdapter().apply {
                items = wordList
            }
            (getChildAt(0) as RecyclerView).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            clipToPadding = false
            clipChildren = false
            offscreenPageLimit = 1
            setPageTransformer { page, position ->
                val offsetPx = resources.getDimensionPixelOffset(R.dimen.offset)
                val pageMarginPx = resources.getDimensionPixelOffset(R.dimen.pageMargin)
                val viewPager = page.parent.parent as ViewPager2
                val offset = position * -(2 * offsetPx + pageMarginPx)
                if (viewPager.orientation == ORIENTATION_HORIZONTAL) {
                    if (ViewCompat.getLayoutDirection(viewPager) == ViewCompat.LAYOUT_DIRECTION_RTL) {
                        page.translationX = -offset
                    } else {
                        page.translationX = offset
                    }
                } else {
                    page.translationY = offset
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setIndicator() {
        binding.viewPagerIndicator.post() {
            val layoutParams = binding.indicatorView.layoutParams

            layoutParams.width = binding.viewPagerIndicator.width / wordList.size
            binding.indicatorView.layoutParams = layoutParams

            binding.indicatorTextView.text = "1/${wordList.size}"
        }

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                binding.indicatorTextView.text = "${position + 1}/${wordList.size}"
            }

            override fun onPageScrolled(
                position: Int, positionOffset: Float, positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)

                binding.indicatorView.x = (position + positionOffset) * binding.indicatorView.width
            }
        })
    }
}