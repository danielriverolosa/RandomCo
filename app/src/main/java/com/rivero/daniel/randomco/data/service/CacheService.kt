package com.rivero.daniel.randomco.data.service

import javax.inject.Inject


class CacheService<T: Any> @Inject constructor() {

    private val cacheMap: MutableMap<String, T> = mutableMapOf()

    fun put(key: String, data: T) {
        cacheMap[key] = data
    }

    fun get(key: String): T? {
        return cacheMap[key]
    }

    fun getAllAsList(): List<T> = cacheMap.values.toList()

    fun hasData(): Boolean = cacheMap.isNotEmpty()

    fun clear() {
        cacheMap.clear()
    }

}