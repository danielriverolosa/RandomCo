package com.rivero.daniel.randomco.domain.usecase.user

import com.rivero.daniel.randomco.domain.User
import com.rivero.daniel.randomco.domain.usecase.UseCaseCallback


interface UserListUseCase {
    fun execute(callback: UseCaseCallback<List<User>>)
}