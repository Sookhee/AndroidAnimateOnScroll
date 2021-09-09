package com.example.animateonscrollexample

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.ViewPropertyAnimator
import android.widget.LinearLayout
import androidx.core.widget.NestedScrollView

class CustomViewHolder @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var isVisible = false // 현재 보여지고 있는지 (두 번 애니메이션 시키지 않기 위해서)
    private val location = IntArray(2)

    fun setWindowListener() {

        setScrollListener()
    }

    @SuppressLint("NewApi")
    private fun setScrollListener() {
        (parent.parent as NestedScrollView).viewTreeObserver.addOnScrollChangedListener {
            getLocationOnScreen(location)

            if (!isVisible && location[1] <= (parent.parent as NestedScrollView).height + ANIMATION_GUIDE_LINE) {
                isVisible = true
                animationShow()
            }
        }
    }

    private fun animationShow() {
        val animator: ViewPropertyAnimator = this.animate()
            .translationY(0f)
            .alpha(1f)
            .setDuration(ANIMATION_DURATION)

        animator.start()
    }

    companion object {
        private const val ANIMATION_GUIDE_LINE: Int = 500
        private const val ANIMATION_DURATION: Long = 1000
    }
}
