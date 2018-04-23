package com.rivero.daniel.randomco.presentation.main.presenter

import com.rivero.daniel.randomco.domain.Location
import com.rivero.daniel.randomco.domain.User
import com.rivero.daniel.randomco.domain.usecase.UseCaseCallback
import com.rivero.daniel.randomco.domain.usecase.register.RegisterUserUseCase
import com.rivero.daniel.randomco.presentation.base.BasePresenter
import com.rivero.daniel.randomco.presentation.main.view.RegisterUserView
import java.util.*
import javax.inject.Inject


class RegisterUserPresenter @Inject constructor(
        private val registerUserUseCase: RegisterUserUseCase
): BasePresenter<RegisterUserView>() {

    fun onClickConfirm() {
        val user = buildUser()
        registerUserUseCase.execute(user, object: UseCaseCallback<Unit> {
            override fun onSuccess(data: Unit) {
                view.close()
            }

            override fun onError(t: Throwable) {
                view.showMessage(t.message ?: "Error in user registration")
            }

        })
    }

    private fun buildUser(): User = User(
            view.getUsername(),
            view.getName(),
            view.getSurname(),
            view.getGender(),
            view.getEmail(),
            view.getPhone(),
            Date(),
            Location(view.getStreet(), view.getCity(), view.getState()),
            favorite = true
    )

}