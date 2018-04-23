package com.rivero.daniel.randomco.presentation.main.model

import java.io.Serializable


sealed class UserListType: Serializable {

    class Normal: UserListType()

    class Favorite: UserListType()

}