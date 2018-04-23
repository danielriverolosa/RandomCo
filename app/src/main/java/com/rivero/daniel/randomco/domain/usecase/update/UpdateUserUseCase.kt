package com.rivero.daniel.randomco.domain.usecase.update

import com.rivero.daniel.randomco.domain.User
import com.rivero.daniel.randomco.domain.usecase.UseCaseCallback


interface UpdateUserUseCase {
    fun execute(user: User, callback: UseCaseCallback<User>)
}