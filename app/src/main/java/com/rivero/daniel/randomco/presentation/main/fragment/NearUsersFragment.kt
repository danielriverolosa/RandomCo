package com.rivero.daniel.randomco.presentation.main.fragment

import android.Manifest
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.rivero.daniel.randomco.R
import com.rivero.daniel.randomco.domain.User
import com.rivero.daniel.randomco.infrastructure.di.component.ViewComponent
import com.rivero.daniel.randomco.presentation.base.BaseActivity
import com.rivero.daniel.randomco.presentation.base.BaseFragment
import com.rivero.daniel.randomco.presentation.base.utils.hide
import com.rivero.daniel.randomco.presentation.base.utils.show
import com.rivero.daniel.randomco.presentation.main.adapter.UserListAdapter
import com.rivero.daniel.randomco.presentation.main.presenter.NearUsersPresenter
import com.rivero.daniel.randomco.presentation.main.view.NearUsersView
import kotlinx.android.synthetic.main.fragment_near_users.*
import javax.inject.Inject


class NearUsersFragment : BaseFragment(), NearUsersView, MultiplePermissionsListener {

    @Inject
    lateinit var presenter: NearUsersPresenter

    private val adapter by lazy { UserListAdapter() }

    companion object {
        fun getInstance(): NearUsersFragment {
            return NearUsersFragment()
        }
    }

    override val layoutContainer: Int
        get() = R.layout.fragment_near_users

    override fun initializeInjector(viewComponent: ViewComponent) {
        viewComponent.inject(this)
    }

    override fun onStart() {
        super.onStart()
        presenter.onStartLocationService()
    }

    override fun onStop() {
        super.onStop()
        presenter.onStopLocationService()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onAttach(this)

        adapter.onClickDelete = presenter::onClickDelete
        adapter.onClickFavorite = presenter::onClickAddToFavorites
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_near_users, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when (item?.itemId) {
        R.id.action_current_location -> {
            presenter.requestLocation()
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

    override fun needToRequestPermissions() {
        Dexter.withActivity(context as BaseActivity)
                .withPermissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                .withListener(this)
                .check()
    }

    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
        presenter.requestLocation()
    }

    override fun onPermissionRationaleShouldBeShown(permissions: MutableList<PermissionRequest>?, token: PermissionToken?) {
        showEmptyView()
    }

    override fun onItemDeleted(user: User) {
        adapter.notifyItemDeleted(user)
    }

    override fun onItemModified(user: User) {
        adapter.notifyItemModified(user)
    }

}