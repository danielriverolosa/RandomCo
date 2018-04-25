package com.rivero.daniel.randomco.presentation.main.presenter

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import com.rivero.daniel.randomco.domain.User
import com.rivero.daniel.randomco.domain.usecase.delete.DeleteUserUseCase
import com.rivero.daniel.randomco.domain.usecase.update.UpdateUserUseCase
import com.rivero.daniel.randomco.domain.usecase.user.UserListUseCase
import com.rivero.daniel.randomco.presentation.main.model.UserListType
import com.rivero.daniel.randomco.presentation.main.view.UserListView
import com.rivero.daniel.randomco.test.help.mockUser
import com.rivero.daniel.randomco.test.help.onAnswer
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UserListPresenterTest {

    @InjectMocks
    private lateinit var presenter: UserListPresenter

    @Mock
    private lateinit var view: UserListView

    @Mock
    private lateinit var userListUseCase: UserListUseCase

    @Mock
    private lateinit var deleteUserUseCase: DeleteUserUseCase

    @Mock
    private lateinit var updateUserUseCase: UpdateUserUseCase

    @Before
    @Throws(Exception::class)
    fun setUp() {
        presenter.onAttach(view)
    }

    @Test
    @Throws(Exception::class)
    fun onAttach_whenLoadUserList_shouldLoadData() {
        Mockito.doAnswer {
            it.onAnswer<User, List<User>> { listOf(mockUser()) }
        }.`when`(userListUseCase).execute(any())

        Mockito.`when`(view.getListType()).thenReturn(UserListType.Normal())

        presenter.onAttach(view)

        verify(view).onLoadData(any())
    }

    @Test
    @Throws(Exception::class)
    fun onAttach_whenLoadUserListEmpty_shouldEmptyView() {
        Mockito.doAnswer {
            it.onAnswer<User, List<User>> { emptyList() }
        }.`when`(userListUseCase).execute(any())
        Mockito.`when`(view.getListType()).thenReturn(UserListType.Normal())

        presenter.onAttach(view)

        verify(view).showEmptyView()
    }

    @Test
    @Throws(Exception::class)
    fun onClickDelete_whenClickDelete_shouldNotifyToView() {
        presenter.userList = mutableListOf(mockUser(), mockUser())
        Mockito.doAnswer {
            it.onAnswer<User, User> { mockUser() }
        }.`when`(deleteUserUseCase).execute(any(), any())

        presenter.onClickDelete(mockUser())

        verify(view, never()).showEmptyView()
        verify(view).onUserDeleted(any())
    }

    @Test
    @Throws(Exception::class)
    fun onClickDelete_whenClickDelete_shouldShowEmptyView() {
        presenter.userList = mutableListOf(mockUser())
        Mockito.doAnswer {
            it.onAnswer<User, User> { mockUser() }
        }.`when`(deleteUserUseCase).execute(any(), any())

        presenter.onClickDelete(mockUser())

        verify(view).showEmptyView()
        verify(view).onUserDeleted(any())
    }

    @Test
    @Throws(Exception::class)
    fun test() {
        presenter.userList = mutableListOf(mockUser())
        Mockito.doAnswer {
            it.onAnswer<User, User> { mockUser() }
        }.`when`(updateUserUseCase).execute(any(), any())

        presenter.onClickAddToFavorites(mockUser())

        verify(view).onUserModified(any())
    }

}