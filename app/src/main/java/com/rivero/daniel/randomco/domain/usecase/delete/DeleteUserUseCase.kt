package com.rivero.daniel.randomco.domain.usecase.delete

import com.rivero.daniel.randomco.domain.User
import com.rivero.daniel.randomco.domain.usecase.UseCaseCallback


interface DeleteUserUseCase {
    fun execute(user: User, callback: UseCaseCallback<User>)
}