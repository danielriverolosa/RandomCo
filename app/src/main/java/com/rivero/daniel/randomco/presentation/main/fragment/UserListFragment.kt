package com.rivero.daniel.randomco.presentation.main.fragment

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import com.rivero.daniel.randomco.R
import com.rivero.daniel.randomco.domain.User
import com.rivero.daniel.randomco.infrastructure.di.component.ViewComponent
import com.rivero.daniel.randomco.presentation.base.BaseFragment
import com.rivero.daniel.randomco.presentation.base.utils.getParamsByClass
import com.rivero.daniel.randomco.presentation.base.utils.hide
import com.rivero.daniel.randomco.presentation.base.utils.onQueryTextChange
import com.rivero.daniel.randomco.presentation.base.utils.setParamsByClass
import com.rivero.daniel.randomco.presentation.base.utils.show
import com.rivero.daniel.randomco.presentation.main.adapter.UserListAdapter
import com.rivero.daniel.randomco.presentation.main.model.UserListType
import com.rivero.daniel.randomco.presentation.main.presenter.UserListPresenter
import com.rivero.daniel.randomco.presentation.main.view.UserListView
import kotlinx.android.synthetic.main.fragment_user_list.*
import javax.inject.Inject


class UserListFragment : BaseFragment(), UserListView {

    @Inject
    lateinit var presenter: UserListPresenter

    private val adapter by lazy { UserListAdapter() }

    companion object {
        fun getInstance(typeList: UserListType): UserListFragment {
            val fragment = UserListFragment()
            fragment.setParamsByClass(typeList, UserListType::class.java.simpleName)
            return fragment
        }
    }

    override val layoutContainer: Int
        get() = R.layout.fragment_user_list

    override fun initializeInjector(viewComponent: ViewComponent) {
        viewComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onAttach(this)

        adapter.onItemClick = presenter::onClickUser
        adapter.onClickDelete = presenter::onClickDelete
        adapter.onClickFavorite = presenter::onClickAddToFavorites
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_user_list, menu)
        configureSearch(menu?.findItem(R.id.action_search))
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun configureSearch(menuItem: MenuItem?) {
        menuItem?.let {
            val searchView = it.actionView as SearchView
            searchView.onQueryTextChange(presenter::filterUserList)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when (item?.itemId) {
        R.id.action_add -> {
            presenter.onClickAddUser()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onLoadData(userList: List<User>) {
        adapter.swapData(userList)
        loadingView.hide()
        emptyView.hide()
        recyclerView.show()
    }

    override fun showEmptyView() {
        loadingView.hide()
        recyclerView.hide()
        emptyView.show()
    }

    override fun onUserDeleted(user: User) {
        adapter.notifyItemDeleted(user)
    }

    override fun onFilterUsers(userList: List<User>) {
        adapter.swapData(userList)
    }

    override fun onUserModified(user: User) {
        adapter.notifyItemModified(user)
    }

    override fun getListType(): UserListType = getParamsByClass(UserListType::class.java.simpleName)

}