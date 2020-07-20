package com.checkout.sdk.animation

import android.content.Context
import android.support.annotation.AnimRes
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.checkout.sdk.R

class ViewChanger(context: Context) {

    private val context = context.applicationContext
    private val animationSet = HashSet<Animation>()
    private enum class ViewChangeOn { START, END }

    /**
     * Change from one View to another when navigating forward
     *
     * @param viewIn: The view that will be displayed when the change is complete
     * @param viewOut: The view that will disappear when the change is complete
     * @param animate: true if there should be an animation; false if it the views should
     * switch without any animation
     */
    fun changeForward(viewIn: View, viewOut: View, animate: Boolean) {
        transitionOutForward(viewOut, animate)
        transitionInForward(viewIn, animate)
    }

    /**
     * Change from one View to another when navigating backwards
     *
     * @param viewIn: The view that will be displayed when the change is complete
     * @param viewOut: The view that will disappear when the change is complete
     * @param animate: true if there should be an animation; false if it the views should
     * switch without any animation
     */
    fun changeBack(viewIn: View, viewOut: View, animate: Boolean) {
        transitionOutBack(viewOut, animate)
        transitionInBack(viewIn, animate)
    }

    fun cancelAnimations() {
        animationSet.forEach { it.cancel() }
    }

    private fun transitionInForward(view: View, animate: Boolean) {
        slideView(view, R.anim.cko_in_forward, ViewChangeOn.START, View.VISIBLE, animate)
    }

    private fun transitionOutForward(view: View, animate: Boolean) {
        slideView(view, R.anim.cko_out_forward, ViewChangeOn.END, View.GONE, animate)
    }

    private fun transitionOutBack(view: View, animate: Boolean) {
        slideView(view, R.anim.cko_out_back, ViewChangeOn.END, View.GONE, animate)
    }

    private fun transitionInBack(view: View, animate: Boolean) {
        slideView(view, R.anim.cko_in_back, ViewChangeOn.START, View.VISIBLE, animate)
    }

    private fun slideView(
        view: View, @AnimRes animation: Int,
        viewChangeOn: ViewChangeOn,
        viewVisibilityChange: Int,
        animate: Boolean
    ) {
        val viewAnimation = AnimationUtils.loadAnimation(context, animation)
        if (!animate) {
            viewAnimation.duration = 0
        }
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
