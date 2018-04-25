package com.rivero.daniel.randomco.presentation.main.presenter

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doAnswer
import com.nhaarman.mockito_kotlin.verify
import com.rivero.daniel.randomco.domain.User
import com.rivero.daniel.randomco.domain.usecase.delete.DeleteUserUseCase
import com.rivero.daniel.randomco.presentation.main.view.UserDetailView
import com.rivero.daniel.randomco.test.help.mockUser
import com.rivero.daniel.randomco.test.help.onAnswer
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UserDetailPresenterTest {

    @InjectMocks
    private lateinit var presenter: UserDetailPresenter

    @Mock
    private lateinit var view: UserDetailView

    @Mock
    private lateinit var deleteUserUseCase: DeleteUserUseCase

    @Before
    @Throws(Exception::class)
    fun setUp() {
        presenter.onAttach(view)
    }

    @Test
    @Throws(Exception::class)
    fun deleteUser_whenClickDelete_shouldCloseView() {
        presenter.user = mockUser()
        doAnswer { it.onAnswer<User, User> { mockUser() } }.`when`(deleteUserUseCase).execute(any(), any())

        presenter.deleteUser()

        verify(view).close()
    }

    @Test
    @Throws(Exception::class)
    fun deleteUser_whenFailDelete_shouldShowMessage() {
        presenter.user = mockUser()
        doAnswer { it.onAnswer<User, Exception> { Exception() } }.`when`(deleteUserUseCase).execute(any(), any())

        presenter.deleteUser()

        verify(view).showMessage(any())
    }

}