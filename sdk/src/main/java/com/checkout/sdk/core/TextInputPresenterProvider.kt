package com.checkout.sdk.core

import com.checkout.sdk.architecture.PresenterStore
import com.checkout.sdk.cvvinput.CvvInputPresenter


class TextInputPresenterProvider {

    companion object {
        private enum class PresenterKeys(
            val key: String,
            val clazz: Class<CvvInputPresenter>
        ) {
            CVV_PRESENTER("cvv", CvvInputPresenter::class.java)
        }

        fun getOrCreatePresenter(key: String): CvvInputPresenter {
            return PresenterStore.getOrCreateDefault(getPresenterClass(key))
        }

        private fun getPresenterClass(key: String): Class<CvvInputPresenter> {
            return when (key) {
                PresenterKeys.CVV_PRESENTER.key -> PresenterKeys.CVV_PRESENTER.clazz
                else -> {
                    throw IllegalArgumentException("Unknown class key: $key")
                }
            }

        }
    }
}
