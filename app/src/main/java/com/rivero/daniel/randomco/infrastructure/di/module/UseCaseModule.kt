package com.rivero.daniel.randomco.infrastructure.di.module

import com.rivero.daniel.randomco.domain.usecase.delete.DeleteUserAsyncUseCase
import com.rivero.daniel.randomco.domain.usecase.delete.DeleteUserUseCase
import com.rivero.daniel.randomco.domain.usecase.register.RegisterUserAsyncUseCase
import com.rivero.daniel.randomco.domain.usecase.register.RegisterUserUseCase
import com.rivero.daniel.randomco.domain.usecase.update.UpdateUserAsyncUseCase
import com.rivero.daniel.randomco.domain.usecase.update.UpdateUserUseCase
import com.rivero.daniel.randomco.domain.usecase.user.UserListAsyncUseCase
import com.rivero.daniel.randomco.domain.usecase.user.UserListUseCase
import com.rivero.daniel.randomco.infrastructure.di.scope.ViewScope
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface UseCaseModule {

    @Binds
    @ViewScope
    fun providesUserListUseCase(userListAsyncUseCase: UserListAsyncUseCase): UserListUseCase

    @Binds
    @ViewScope
    fun providesRegisterUserUseCase(registerUserAsyncUseCase: RegisterUserAsyncUseCase): RegisterUserUseCase

    @Binds
    @ViewScope
    fun providesDeleteUserUseCase(deleteUserAsyncUseCase: DeleteUserAsyncUseCase): DeleteUserUseCase

    @Binds
    @ViewScope
    fun providesUpdateUserUseCase(updateUserAsyncUseCase: UpdateUserAsyncUseCase): UpdateUserUseCase

}