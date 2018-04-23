package com.rivero.daniel.randomco.presentation.main.view

import com.rivero.daniel.randomco.domain.User
import com.rivero.daniel.randomco.presentation.base.BaseView


interface NearUsersView: BaseView {

    fun onLoadData(userList: List<User>)

    fun showEmptyView()

    fun needToRequestPermissions()

    fun onItemDeleted(user: User)

    fun onItemModified(user: User)

}