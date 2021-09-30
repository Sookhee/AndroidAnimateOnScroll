package com.example.animateonscrollexample

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import android.view.ViewPropertyAnimator
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.NestedScrollView

class AnimationWrapper @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    var customY: Float = 20.dp
    var customAlpha: Float = 0.2f

    @SuppressLint("CustomViewStyleable")
    var delay = context.obtainStyledAttributes(attrs, R.styleable.CustomViewHolder)
        .getInt(R.styleable.CustomViewHolder_delayAnimation, 0).toLong()

    private var isVisible = false // 현재 보여지고 있는지 (두 번 애니메이션 시키지 않기 위해서)
    private val location = IntArray(2)

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        this.y = customY
        this.alpha = customAlpha

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

    private fun showIfINeed(yPosition: Int) {
        if (!isVisible && yPosition <= (parent.parent as NestedScrollView).height + ANIMATION_GUIDE_LINE) {
            isVisible = true
            startAnimation()
        }
    }

    private fun startAnimation() {
        val animator: ViewPropertyAnimator = this.animate()
            .translationY(0f)
            .alpha(1f)
            .setDuration(ANIMATION_DURATION)
            .setStartDelay(delay)

        animator.start()
    }

    companion object {
        private const val ANIMATION_GUIDE_LINE: Int = 500
        private const val ANIMATION_DURATION: Long = 1000

        val Int.dp: Float
            get() = (this * Resources.getSystem().displayMetrics.density + 0.5f)
    }
}
