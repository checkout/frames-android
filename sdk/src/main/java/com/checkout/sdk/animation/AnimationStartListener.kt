package com.checkout.sdk.animation

import android.view.animation.Animation


abstract class AnimationStartListener : Animation.AnimationListener {
    final override fun onAnimationRepeat(animation: Animation?) {
        // Nothing
    }

    final override fun onAnimationEnd(animation: Animation?) {
        // Nothing
    }

}
