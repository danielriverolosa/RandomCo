package com.rivero.daniel.randomco.presentation.main.presenter

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.verify
import com.rivero.daniel.randomco.domain.User
import com.rivero.daniel.randomco.domain.usecase.register.RegisterUserUseCase
import com.rivero.daniel.randomco.presentation.main.view.RegisterUserView
import com.rivero.daniel.randomco.test.help.onAnswer
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.doAnswer
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RegisterUserPresenterTest {

    @InjectMocks
    private lateinit var presenter: RegisterUserPresenter

    @Mock
    private lateinit var registerUserUseCase: RegisterUserUseCase

    @Mock
    private lateinit var view: RegisterUserView

    @Before
    @Throws(Exception::class)
    fun setUp() {
        presenter.onAttach(view)

        `when`(view.getName()).thenReturn("name")
        `when`(view.getSurname()).thenReturn("surname")
        `when`(view.getUsername()).thenReturn("username")
        `when`(view.getGender()).thenReturn("gender")
        `when`(view.getEmail()).thenReturn("email")
        `when`(view.getPhone()).thenReturn("phone")
        `when`(view.getStreet()).thenReturn("street")
        `when`(view.getCity()).thenReturn("city")
        `when`(view.getState()).thenReturn("state")
    }

    @Test
    @Throws(Exception::class)
    fun onClickConfirm_whenRegisterUser_shouldCloseView() {
        doAnswer{
            it.onAnswer<User, Unit> { }
        }.`when`(registerUserUseCase).execute(any(), any())

        presenter.onClickConfirm()

        verify(view).close()
    }


    @Test
    @Throws(Exception::class)
    fun onClickConfirm_whenFailRegisterUser_shouldShowMessage() {
        doAnswer{
            it.onAnswer<User, Exception> { Exception() }
        }.`when`(registerUserUseCase).execute(any(), any())

        presenter.onClickConfirm()

        verify(view).showMessage(any())
    }

}