package com.rivero.daniel.randomco.data.datasource.local.user.entity


class LocationEntity (
        val street: String,
        val city: String,
        val state: String,
        val postcode: String?,
        var latitude: Double? = null,
        var longitude: Double? = null
)