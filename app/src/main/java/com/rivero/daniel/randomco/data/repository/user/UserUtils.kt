package com.rivero.daniel.randomco.data.repository.user

import com.rivero.daniel.randomco.data.datasource.api.user.model.LocationResponse
import com.rivero.daniel.randomco.data.datasource.api.user.model.PictureResponse
import com.rivero.daniel.randomco.data.datasource.api.user.model.UserListResponse
import com.rivero.daniel.randomco.data.datasource.local.user.entity.LocationEntity
import com.rivero.daniel.randomco.data.datasource.local.user.entity.PictureEntity
import com.rivero.daniel.randomco.data.datasource.local.user.entity.UserEntity
import com.rivero.daniel.randomco.domain.Location
import com.rivero.daniel.randomco.domain.User
import com.rivero.daniel.randomco.domain.UserPicture
import java.util.*


fun UserEntity.map(): User = User(
        username,
        name,
        surname,
        gender,
        email,
        phone,
        Date(registered),
        location.map(),
        picture?.map(),
        favorite
)

fun LocationEntity.map(): Location = Location(street, city, state, postcode, latitude, longitude)

fun PictureEntity?.map(): UserPicture? =
        if (this != null) UserPicture(large, medium, thumbnail) else null


fun UserListResponse.map(): List<UserEntity> = results.map { userResponse ->
    UserEntity(
            username = userResponse.login.username,
            name = userResponse.name.first,
            surname = userResponse.name.last,
            gender = userResponse.gender,
            email = userResponse.email,
            phone = userResponse.phone,
            registered = userResponse.registered.time,
            location = userResponse.location.map(),
            picture = userResponse.picture.map()
    )
}

fun LocationResponse.map(): LocationEntity =
        LocationEntity(street, city, state, postcode)

fun PictureResponse.map(): PictureEntity = PictureEntity(large, medium, thumbnail)

fun User.map(): UserEntity = UserEntity(
        username,
        name,
        surname,
        gender,
        email,
        phone,
        registered.time,
        location.map(),
        picture?.map(),
        favorite
)

fun Location.map(): LocationEntity = LocationEntity(street, city, state, postcode)

fun UserPicture.map(): PictureEntity = PictureEntity(large, medium, thumbnail)
