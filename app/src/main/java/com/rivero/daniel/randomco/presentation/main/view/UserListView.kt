package com.rivero.daniel.randomco.presentation.main.view

import com.rivero.daniel.randomco.domain.User
import com.rivero.daniel.randomco.presentation.base.BaseView
import com.rivero.daniel.randomco.presentation.main.model.UserListType


interface UserListView: BaseView {

    fun onLoadData(userList: List<User>)

    fun onUserDeleted(user: User)

    fun onFilterUsers(userList: List<User>)

    fun onUserModified(user: User)

    fun getListType(): UserListType

    fun showEmptyView()
}