package com.rivero.daniel.randomco.data.service

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import com.rivero.daniel.randomco.domain.service.LocationService
import javax.inject.Inject


class LocationGoogleService @Inject constructor(
        private val context: Context
) : LocationService, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private var googleApiClient: GoogleApiClient? = null
    private var locationServiceListener: LocationServiceListener? = null

    override fun init() {
        if (googleApiClient == null) {
            googleApiClient = GoogleApiClient.Builder(context)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build()
        }
    }

    override fun onStart() {
        googleApiClient?.connect()
    }

    override fun onStop() {
        googleApiClient?.disconnect()
    }

    override fun requestLocation() {
        if (isConnected()) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                locationServiceListener?.onPermissionDenied()
            } else {
                LocationServices.getFusedLocationProviderClient(context)
                        .lastLocation
                        .addOnCompleteListener { notifyLocation(it.result) }
            }
        }
    }

    private fun isConnected(): Boolean {
        return googleApiClient?.isConnected == true || googleApiClient?.isConnecting == true
    }

    private fun notifyLocation(location: Location?) {
        if (location != null) {
            locationServiceListener?.onLocation(location)
        } else {
            locationServiceListener?.onLocationNotFound()
        }
    }

    override fun setLocationListener(locationServiceListener: LocationServiceListener) {
        this.locationServiceListener = locationServiceListener
    }

    override fun onConnected(bundle: Bundle?) {
        requestLocation()
    }

    override fun onConnectionSuspended(i: Int) {

    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {

    }

    interface LocationServiceListener {
        fun onLocation(location: Location)

        fun onLocationNotFound()

        fun onPermissionDenied()
    }

}