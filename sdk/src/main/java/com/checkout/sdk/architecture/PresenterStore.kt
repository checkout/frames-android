package com.checkout.sdk.architecture


object PresenterStore {

    private val presenterMap = mutableMapOf<String, BasePresenter<*,*>>()

    /**
     * Gets a Presenter of the specified class from the map if it has already been created.
     * If the Presenter is not already created, creates a Presenter of the specified class,
     * invoking the `functionToCreate` in order to do so. If you want multiple presenters of
     * the same class then you must provide a unique key to create and get that presenter.
     * This method must be used (in preference to getOrCreateDefault) when the Presenter has
     * no default constructor
     */
    @Suppress("UNCHECKED_CAST")
    @Synchronized
    fun <T : BasePresenter<*,*>> getOrCreate(
        classToGet: Class<T>,
        functionToCreate: () -> T,
        uniqueKey: String = ""
    ): T {
        val mapKey = classToGet.simpleName.addSuffixIfKeyNotEmpty(uniqueKey)
        if (!presenterMap.containsKey(mapKey)) {
            presenterMap[mapKey] = functionToCreate.invoke()
        }
        return presenterMap[mapKey]!! as T
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
        classToGet: Class<T>,
        uniqueKey: String = ""
    ): T {
        return getOrCreate(classToGet, { classToGet.newInstance() }, uniqueKey)
    }
}

private fun String.addSuffixIfKeyNotEmpty(uniqueKey: String): String {
    return if (uniqueKey.isEmpty()) {
        this
    } else {
        this.plus("_$uniqueKey")
    }
}
