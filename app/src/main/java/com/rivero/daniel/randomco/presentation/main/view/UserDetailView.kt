package com.rivero.daniel.randomco.presentation.main.view

import com.rivero.daniel.randomco.domain.User
import com.rivero.daniel.randomco.presentation.base.BaseView


interface UserDetailView: BaseView {

    fun setImageProfile(image: String?)

    fun fillData(user: User)

}