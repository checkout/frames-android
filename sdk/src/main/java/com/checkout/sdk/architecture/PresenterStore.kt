package com.checkout.sdk.architecture


object PresenterStore {

    private val presenterMap = mutableMapOf<String, BasePresenter<*,*>>()

    /**
     * Gets a Presenter of the specified class from the map if it has already been created.
     * If the Presenter is not already created, creates a Presenter of the specified class,
     * invoking the `functionToCreate` in order to do so. This method must be used (in
     * preference to getOrCreateDefault) when the Presenter has no default constructor
     */
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

    /**
     * Gets a Presenter of the specified class from the map if it has already been created.
     * If the Presenter is not already created, creates a Presenter of the specified class
     * with the requirement that the Presenter has a default (no-args) constructor. This
     * Presenter will be inserted in the map for future use.
     */
    @Suppress("UNCHECKED_CAST")
    @Synchronized
    fun <T : BasePresenter<*,*>> getOrCreateDefault(
        classToGet: Class<T>
    ): T {
        if (!presenterMap.containsKey(classToGet.simpleName)) {
            presenterMap[classToGet.simpleName] = classToGet.newInstance()
        }
        return presenterMap[classToGet.simpleName]!! as T
    }
}
