package com.rivero.daniel.randomco.domain.usecase.delete

import com.rivero.daniel.randomco.domain.User
import com.rivero.daniel.randomco.domain.repository.UserRepository
import com.rivero.daniel.randomco.domain.usecase.UseCase
import com.rivero.daniel.randomco.domain.usecase.UseCaseCallback
import javax.inject.Inject

class DeleteUserUseCase @Inject constructor(
        val repository: UserRepository
) : UseCase<User>() {

    fun execute(user: User, callback: UseCaseCallback<User>) {
        executeAsync(callback, user)
    }

    override fun runInBackground(vararg params: Any): User {
        val user = params[0] as User
        repository.deleteUser(user)
        return user
    }
}