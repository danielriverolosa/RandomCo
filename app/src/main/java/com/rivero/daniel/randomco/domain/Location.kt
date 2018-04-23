package com.rivero.daniel.randomco.domain


class Location (
        val street: String,
        val city: String,
        val state: String,
        var postcode: String? = null,
        var latitude: Double? = null,
        var longitude: Double? = null
)