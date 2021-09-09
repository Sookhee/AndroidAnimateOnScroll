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
    private var delay: Long = 0

    fun setWindowListener() {
        (parent.parent as NestedScrollView).viewTreeObserver.addOnGlobalLayoutListener {
            getLocationOnScreen(location)

            showIfINeed(location[1])
        }

        setScrollListener()
    }

    @SuppressLint("NewApi")
    private fun setScrollListener() {
        (parent.parent as NestedScrollView).viewTreeObserver.addOnScrollChangedListener {
            getLocationOnScreen(location)

            showIfINeed(location[1])
        }
    }

    private fun showIfINeed(yPosition : Int) {
        if (!isVisible && yPosition <= (parent.parent as NestedScrollView).height + ANIMATION_GUIDE_LINE) {
            isVisible = true
            showAnimation()
        }
    }

    private fun showAnimation() {
        val animator: ViewPropertyAnimator = this.animate()
            .translationY(0f)
            .alpha(1f)
            .setDuration(ANIMATION_DURATION)
            .setStartDelay(delay)

        animator.start()
    }

    fun setAnimationDelay(delay: Long) {
        this.delay = delay
    }

    companion object {
        private const val ANIMATION_GUIDE_LINE: Int = 500
        private const val ANIMATION_DURATION: Long = 1000
    }
}
