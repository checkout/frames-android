package com.checkout.sdk.animation

import android.content.Context
import android.support.annotation.AnimRes
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.checkout.sdk.R

class SlidingViewAnimator(context: Context) {

    private val context = context.applicationContext
    private val animationSet = HashSet<Animation>()
    private enum class ViewChangeOn { START, END }


    fun transitionInFromRight(viewInFromRight: View, viewOutToLeft: View) {
        slideOutToLeft(viewOutToLeft)
        slideInFromRight(viewInFromRight)
    }

    fun transitionOutToRight(viewOutToRight: View, viewInFromLeft: View) {
        slideOutToRight(viewOutToRight)
        slideInFromLeft(viewInFromLeft)
    }

    fun cancelAnimations() {
        animationSet.forEach { it.cancel() }
    }

    private fun slideInFromRight(view: View) {
        slideView(view, R.anim.slide_in_right, ViewChangeOn.START, View.VISIBLE)
    }

    private fun slideOutToLeft(view: View) {
        slideView(view, R.anim.slide_out_left, ViewChangeOn.END, View.GONE)
    }

    private fun slideOutToRight(view: View) {
        slideView(view, R.anim.slide_out_right, ViewChangeOn.END, View.GONE)
    }

    private fun slideInFromLeft(view: View) {
        slideView(view, R.anim.slide_in_left, ViewChangeOn.START, View.VISIBLE)
    }

    private fun slideView(
        view: View, @AnimRes animation: Int,
        viewChangeOn: ViewChangeOn,
        viewVisibilityChange: Int
    ) {
        val viewAnimation = AnimationUtils.loadAnimation(context, animation)
        val animationListener = when (viewChangeOn) {
            ViewChangeOn.START -> object : AnimationStartListener() {
                override fun onAnimationStart(animation: Animation) {
                    view.visibility = viewVisibilityChange
                }
            }
            ViewChangeOn.END -> object : AnimationEndListener() {
                override fun onAnimationEnd(animation: Animation) {
                    view.visibility = viewVisibilityChange
                }
            }
        }
        viewAnimation.setAnimationListener(animationListener)
        view.startAnimation(viewAnimation)
        animationSet.add(viewAnimation)
    }
}
