package com.rivero.daniel.randomco.presentation.main.presenter

import com.rivero.daniel.randomco.domain.User
import com.rivero.daniel.randomco.domain.usecase.UseCaseCallback
import com.rivero.daniel.randomco.domain.usecase.delete.DeleteUserUseCase
import com.rivero.daniel.randomco.domain.usecase.update.UpdateUserUseCase
import com.rivero.daniel.randomco.domain.usecase.user.UserListUseCase
import com.rivero.daniel.randomco.presentation.base.BasePresenter
import com.rivero.daniel.randomco.presentation.main.model.UserListType
import com.rivero.daniel.randomco.presentation.main.view.UserListView
import javax.inject.Inject


class UserListPresenter @Inject constructor(
        private val userListUseCase: UserListUseCase,
        private val deleteUserUseCase: DeleteUserUseCase,
        private val updateUserUseCase: UpdateUserUseCase
): BasePresenter<UserListView>() {

    var userList: MutableList<User> = mutableListOf()

    override fun onAttach(view: UserListView) {
        super.onAttach(view)
        loadUserList()
    }

    private fun loadUserList() {
        userListUseCase.execute(object : UseCaseCallback<List<User>>{
            override fun onSuccess(data: List<User>) {
                userList = filterListByType(data)
                val sortedListByName = userList.sortedBy { it.name.capitalize() }
                checkShowEmptyViewOrDoAction { view.onLoadData(sortedListByName) }
            }

            override fun onError(t: Throwable) {
                view.showMessage(t.message ?: "Error when load user list")
            }
        })
    }

    private fun filterListByType(userList: List<User>): MutableList<User> {
        val listType = view.getListType()
        return when(listType) {
            is UserListType.Normal -> userList.toMutableList()
            is UserListType.Favorite -> userList.filter { it.favorite }.toMutableList()
        }
    }

    private fun checkShowEmptyViewOrDoAction(action: () -> Unit) {
        if (userList.isEmpty()) {
            view.showEmptyView()
        } else {
            action.invoke()
        }
    }

    fun onClickAddUser() {
        navigator.showRegisterUser()
    }

    fun onClickUser(user: User) {
        navigator.showUserDetail(user)
    }

    fun onClickDelete(user: User) {
        deleteUserUseCase.execute(user, object : UseCaseCallback<User>{
            override fun onSuccess(data: User) {
                val index = userList.indexOfFirst { it.username == data.username }
                userList.removeAt(index)

                if (userList.isEmpty()) view.showEmptyView()

                view.onUserDeleted(data)
            }

            override fun onError(t: Throwable) {
                view.showMessage(t.message ?: "Error when remove user")
            }
        })
    }

    fun onClickAddToFavorites(user: User) {
        user.favorite = true
        updateUserUseCase.execute(user, object: UseCaseCallback<User>{
            override fun onSuccess(data: User) {
                val index = userList.indexOfFirst { it.username == data.username }
                userList[index] = user
                view.onUserModified(user)
            }

            override fun onError(t: Throwable) {
                view.showMessage(t.message ?: "Error when add to favorites")
            }
        })
    }

    fun filterUserList(text: String) {
        val filteredList = userList.filter { user ->
            containsText(user, text.toLowerCase())
        }
        view.onFilterUsers(filteredList)
    }

    private fun containsText(user: User, text: String): Boolean {
        return user.name.toLowerCase().contains(text) || user.surname.contains(text) || user.email.contains(text)
    }

}