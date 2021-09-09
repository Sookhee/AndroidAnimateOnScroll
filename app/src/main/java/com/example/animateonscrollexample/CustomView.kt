package com.example.animateonscrollexample

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import com.example.animateonscrollexample.databinding.ViewHolderBinding

class CustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    fun addColorView(color: String) {
        val inflater = LayoutInflater.from(context)
        val binding: ViewHolderBinding = DataBindingUtil.inflate(inflater, R.layout.view_holder, null, false)

        val params = LayoutParams(LayoutParams.MATCH_PARENT, 700)
        binding.customViewHolder.setBackgroundColor(Color.parseColor(color))
        binding.customViewHolder.y = 900f
        binding.customViewHolder.alpha = 0f

        addView(binding.root, params)

        binding.customViewHolder.setWindowListener()
    }
}
