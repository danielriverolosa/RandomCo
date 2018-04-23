package com.rivero.daniel.randomco.presentation.main.presenter

import android.location.Location
import com.rivero.daniel.randomco.data.service.LocationGoogleService
import com.rivero.daniel.randomco.domain.User
import com.rivero.daniel.randomco.domain.service.LocationService
import com.rivero.daniel.randomco.domain.usecase.UseCaseCallback
import com.rivero.daniel.randomco.domain.usecase.delete.DeleteUserUseCase
import com.rivero.daniel.randomco.domain.usecase.update.UpdateUserUseCase
import com.rivero.daniel.randomco.domain.usecase.user.UserListUseCase
import com.rivero.daniel.randomco.presentation.base.BasePresenter
import com.rivero.daniel.randomco.presentation.main.view.NearUsersView
import javax.inject.Inject


class NearUsersPresenter @Inject constructor(
        private val locationService: LocationService,
        private val userListUseCase: UserListUseCase,
        private val deleteUserUseCase: DeleteUserUseCase,
        private val updateUserUseCase: UpdateUserUseCase
) : BasePresenter<NearUsersView>(), LocationGoogleService.LocationServiceListener {

    override fun onAttach(view: NearUsersView) {
        super.onAttach(view)
        locationService.init()
        locationService.setLocationListener(this)
        locationService.requestLocation()
    }

    fun onStartLocationService() {
        locationService.onStart()
    }

    fun onStopLocationService() {
        locationService.onStop()
    }

    fun requestLocation() {
        locationService.requestLocation()
    }

    override fun onLocation(location: Location) {
        userListUseCase.execute(object : UseCaseCallback<List<User>> {
            override fun onSuccess(data: List<User>) {
                val filteredList = data.filter { distanceIsEnough(it, location) }
                if (filteredList.isEmpty()) view.showEmptyView()
                else view.onLoadData(filteredList)
            }

            override fun onError(t: Throwable) {
                view.showMessage(t.message ?: "Error when load user list")
            }

        })
    }

    private fun distanceIsEnough(user: User, location: Location): Boolean {
        if (user.hasCoordinates()) {
            val results = FloatArray(1)
            Location.distanceBetween(location.latitude, location.longitude, user.location.latitude!!, user.location.longitude!!, results)
            return results[0] <= 1000
        }
        return false
    }

    override fun onLocationNotFound() {
        view.showEmptyView()
    }

    override fun onPermissionDenied() {
        view.needToRequestPermissions()
    }

    fun onClickDelete(user: User) {
        deleteUserUseCase.execute(user, object : UseCaseCallback<User>{
            override fun onSuccess(data: User) {
                view.onItemDeleted(data)
            }

            override fun onError(t: Throwable) {
                view.showMessage(t.message ?: "Error when try to delete user")
            }
        })
    }

    fun onClickAddToFavorites(user: User) {
        updateUserUseCase.execute(user, object : UseCaseCallback<User>{
            override fun onSuccess(data: User) {
                view.onItemModified(data)
            }

            override fun onError(t: Throwable) {
                view.showMessage(t.message ?: "Error when try to modify user")
            }
        })
    }

}