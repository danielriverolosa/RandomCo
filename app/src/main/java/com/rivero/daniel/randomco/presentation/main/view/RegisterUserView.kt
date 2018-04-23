package com.rivero.daniel.randomco.presentation.main.view

import com.rivero.daniel.randomco.presentation.base.BaseView


interface RegisterUserView: BaseView {

    fun getName(): String
    fun getSurname(): String
    fun getUsername(): String
    fun getGender(): String
    fun getEmail(): String
    fun getPhone(): String
    fun getStreet(): String
    fun getCity(): String
    fun getState(): String

}