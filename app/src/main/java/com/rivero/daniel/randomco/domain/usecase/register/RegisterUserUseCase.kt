package com.rivero.daniel.randomco.domain.usecase.register

import com.rivero.daniel.randomco.domain.User
import com.rivero.daniel.randomco.domain.usecase.UseCaseCallback


interface RegisterUserUseCase {

    fun execute(user: User, callback: UseCaseCallback<Unit>)

}