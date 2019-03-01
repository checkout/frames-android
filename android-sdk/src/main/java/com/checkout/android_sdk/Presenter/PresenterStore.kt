package com.checkout.android_sdk.Presenter


object PresenterStore {

    private val presenterMap = mutableMapOf<String, Presenter>()

    @Suppress("UNCHECKED_CAST")
    @Synchronized
    fun <T : Presenter> getOrCreate(
        classToGet: Class<T>,
        functionToCreate: () -> Presenter
    ): T {
        if (!presenterMap.containsKey(classToGet.simpleName)) {
            presenterMap[classToGet.simpleName] = functionToCreate.invoke()
        }
        return presenterMap[classToGet.simpleName]!! as T
    }
}
