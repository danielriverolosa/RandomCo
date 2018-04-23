package com.rivero.daniel.randomco.domain.service

import com.rivero.daniel.randomco.data.service.LocationGoogleService


interface LocationService {

    fun init()

    fun onStart()

    fun onStop()

    fun requestLocation()

    fun setLocationListener(locationServiceListener: LocationGoogleService.LocationServiceListener)

}