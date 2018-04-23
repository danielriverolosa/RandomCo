package com.rivero.daniel.randomco.data.datasource.api


interface ApiClientGenerator {
    fun <T> generateApi(service: Class<T>): T
}