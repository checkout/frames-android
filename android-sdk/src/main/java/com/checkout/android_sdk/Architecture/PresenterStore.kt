package com.checkout.android_sdk.Architecture


object PresenterStore {

    private val presenterMap = mutableMapOf<String, BasePresenter<*,*>>()

    @Suppress("UNCHECKED_CAST")
    @Synchronized
    fun <T : BasePresenter<*,*>> getOrCreate(
        classToGet: Class<T>,
        functionToCreate: () -> T
    ): T {
        if (!presenterMap.containsKey(classToGet.simpleName)) {
            presenterMap[classToGet.simpleName] = functionToCreate.invoke()
        }
        return presenterMap[classToGet.simpleName]!! as T
    }
}
