package com.example.animateonscrollexample

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.ViewParent
import android.view.ViewPropertyAnimator
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.NestedScrollView

class AnimationWrapper @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    @SuppressLint("CustomViewStyleable")
    var customDelay = context.obtainStyledAttributes(attrs, R.styleable.CustomViewHolder)
        .getInt(R.styleable.CustomViewHolder_delayAnimation, 0).toLong()

    var customY: Float = 20.dp
    var customAlpha: Float = 0.2f

    private var isVisible = false // 현재 보여지고 있는지 (두 번 애니메이션 시키지 않기 위해서)
    private val location = IntArray(2)

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        var parentScrollView: ViewParent = parent

        while(parentScrollView !is NestedScrollView) {
            parentScrollView = parentScrollView.parent
        }

        val displayMetrics = DisplayMetrics()
        display.getRealMetrics(displayMetrics)

        ANIMATION_GUIDE_LINE = (displayMetrics.heightPixels) / 4 * 3

        this.y = customY
        this.alpha = customAlpha

        parentScrollView.viewTreeObserver.addOnGlobalLayoutListener {
            getLocationOnScreen(location)

            showIfINeed(location[1])
        }

        setScrollListener(parentScrollView)
    }

    @SuppressLint("NewApi")
    private fun setScrollListener(parentScrollView: NestedScrollView) {
        parentScrollView.viewTreeObserver.addOnScrollChangedListener {
            getLocationOnScreen(location)

            showIfINeed(location[1])
        }
    }

    private fun showIfINeed(yPosition: Int) {
        if (!isVisible && yPosition <= ANIMATION_GUIDE_LINE) {
            isVisible = true
            startAnimation()
        }
    }

    private fun startAnimation() {
        val animator: ViewPropertyAnimator = this.animate()
            .translationY(0f)
            .alpha(1f)
            .setDuration(ANIMATION_DURATION)
            .setStartDelay(customDelay)

        animator.start()
    }

    companion object {
        private var ANIMATION_GUIDE_LINE: Int = 0
        private const val ANIMATION_DURATION: Long = 250

        val Int.dp: Float
            get() = (this * Resources.getSystem().displayMetrics.density + 0.5f)
    }
}
