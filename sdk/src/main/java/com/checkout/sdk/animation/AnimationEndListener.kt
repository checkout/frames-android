package com.checkout.sdk.animation

import android.view.animation.Animation


abstract class AnimationEndListener : Animation.AnimationListener {
    final override fun onAnimationRepeat(animation: Animation?) {
        // Nothing
    }

    final override fun onAnimationStart(animation: Animation?) {
        // Nothing
    }

}
