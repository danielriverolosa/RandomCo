package com.rivero.daniel.randomco.data.repository.user

import com.rivero.daniel.randomco.data.datasource.api.ApiClientGenerator
import com.rivero.daniel.randomco.data.datasource.api.user.UserApi
import com.rivero.daniel.randomco.data.datasource.local.user.dao.UserDao
import com.rivero.daniel.randomco.data.datasource.local.user.entity.UserEntity
import com.rivero.daniel.randomco.data.repository.BaseRepository
import com.rivero.daniel.randomco.data.service.CacheService
import com.rivero.daniel.randomco.domain.User
import com.rivero.daniel.randomco.domain.repository.UserRepository
import java.util.*
import javax.inject.Inject


class UserDataRepository @Inject constructor(
        private val apiClientGenerator: ApiClientGenerator,
        private val userDao: UserDao,
        private val cacheService: CacheService<User>
) : BaseRepository(), UserRepository {

    private var refresh: Boolean = false

    override fun getUserList(): List<User> = when {
        cacheService.hasData() -> cacheService.getAllAsList()
        refresh -> getUserListFromApi()
        else -> getUserListFromLocal()
    }

    private fun getUserListFromApi(): List<User> {
        val userApi = apiClientGenerator.generateApi(UserApi::class.java)
        val call = userApi.getUsers()

        val response = executeCall(call)
        val localData = response.map()
        generateRandomLocations(localData)
        refreshLocalData(localData)

        val userList = localData.map { it.map() }
        refreshCache(userList)

        refresh = false

        return userList
    }

    private fun generateRandomLocations(userEntityList: List<UserEntity>) {
        userEntityList.forEach {userEntity ->
            val pair = getRandomLocation( 40.4512225, -3.4498356, 10000)
            userEntity.location.latitude = pair.first
            userEntity.location.longitude = pair.second
        }
    }

    private fun refreshLocalData(userList: List<UserEntity>) {
        userDao.deleteUsers()
        for (user in userList) {
            userDao.insertUser(user)
        }
    }

    private fun refreshCache(userList: List<User>) {
        cacheService.clear()
        for (user in userList ) {
            cacheService.put(user.username, user)
        }
    }

    private fun getUserListFromLocal(): List<User> {
        val userList = userDao.getUsers().map { it.map() }

        return if (userList.isNotEmpty()) {
            refreshCache(userList)
            userList
        } else {
            getUserListFromApi()
        }
    }

    override fun registerUser(user: User) {
        cacheService.clear()
        userDao.insertUser(user.map())
    }

    override fun deleteUser(user: User) {
        cacheService.clear()
        userDao.deleteUserByUsername(user.username)
    }

    override fun updateUser(user: User) {
        cacheService.clear()
        userDao.insertUser(user.map())
    }

    override fun refreshData() {
        refresh = true
    }

    private fun getRandomLocation(latitude: Double, longitude: Double, radiusInMeters: Int): Pair<Double, Double> {
        val random = Random()

        val radiusInDegrees = (radiusInMeters / 111000f).toDouble()

        val u = random.nextDouble()
        val v = random.nextDouble()

        val w = radiusInDegrees * Math.sqrt(u)
        val t = 2.0 * Math.PI * v
        var x = w * Math.cos(t)
        val y = w * Math.sin(t)

        // Adjust the x-coordinate for the shrinking of the east-west distances
        x /= Math.cos(Math.toRadians(longitude))

        val foundLongitude = x + latitude
        val foundLatitude = y + longitude

        return Pair(foundLongitude, foundLatitude)
    }


}