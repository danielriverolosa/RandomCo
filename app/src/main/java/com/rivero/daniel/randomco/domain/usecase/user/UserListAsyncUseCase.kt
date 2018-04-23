package com.rivero.daniel.randomco.domain.usecase.user

import com.rivero.daniel.randomco.domain.User
import com.rivero.daniel.randomco.domain.repository.UserRepository
import com.rivero.daniel.randomco.domain.usecase.UseCase
import com.rivero.daniel.randomco.domain.usecase.UseCaseCallback
import javax.inject.Inject

class UserListAsyncUseCase @Inject constructor(
        private val userRepository: UserRepository
) : UseCase<List<User>>(), UserListUseCase {

    override fun execute(callback: UseCaseCallback<List<User>>) {
        super.execute(callback)
    }

    override fun runInBackground(vararg params: Any): List<User> {
        return userRepository.getUserList()
    }

}