package com.rivero.daniel.randomco.domain.usecase.register

import com.rivero.daniel.randomco.domain.User
import com.rivero.daniel.randomco.domain.repository.UserRepository
import com.rivero.daniel.randomco.domain.usecase.UseCase
import com.rivero.daniel.randomco.domain.usecase.UseCaseCallback
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(val repository: UserRepository) : UseCase<Unit>() {

    fun execute(user: User, callback: UseCaseCallback<Unit>) {
        executeAsync(callback, user)
    }

    override fun runInBackground(vararg params: Any) {
        val user = params[0] as User
        repository.registerUser(user)
    }
}