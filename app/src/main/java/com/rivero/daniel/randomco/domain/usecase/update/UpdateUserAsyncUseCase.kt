package com.rivero.daniel.randomco.domain.usecase.update

import com.rivero.daniel.randomco.domain.User
import com.rivero.daniel.randomco.domain.repository.UserRepository
import com.rivero.daniel.randomco.domain.usecase.UseCase
import com.rivero.daniel.randomco.domain.usecase.UseCaseCallback
import javax.inject.Inject

class UpdateUserAsyncUseCase @Inject constructor(
        private val repository: UserRepository
) : UseCase<User>(), UpdateUserUseCase {

    override fun execute(user: User, callback: UseCaseCallback<User>) {
        execute(callback, user)
    }

    override fun runInBackground(vararg params: Any): User {
        val user = params[0] as User
        repository.updateUser(user)
        return user
    }
}