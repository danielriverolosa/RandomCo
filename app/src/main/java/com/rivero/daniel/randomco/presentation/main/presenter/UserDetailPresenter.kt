package com.rivero.daniel.randomco.presentation.main.presenter

import com.rivero.daniel.randomco.domain.User
import com.rivero.daniel.randomco.domain.usecase.UseCaseCallback
import com.rivero.daniel.randomco.domain.usecase.delete.DeleteUserUseCase
import com.rivero.daniel.randomco.presentation.base.BasePresenter
import com.rivero.daniel.randomco.presentation.main.view.UserDetailView
import javax.inject.Inject


class UserDetailPresenter @Inject constructor(
        private val deleteUserUseCase: DeleteUserUseCase
): BasePresenter<UserDetailView>() {

    var user: User? = null

    fun loadData(user: User) {
        this.user = user
        view.setImageProfile(user.picture?.large)
        view.fillData(user)
    }

    fun deleteUser(){
        val user = this.user
        user?.let { executeDelete(user) }
    }

    private fun executeDelete(user: User) {
        deleteUserUseCase.execute(user, object: UseCaseCallback<User>{
            override fun onSuccess(data: User) {
                view.close()
            }

            override fun onError(t: Throwable) {
                view.showMessage(t.message ?: "Error when remove user")
            }
        })
    }

}