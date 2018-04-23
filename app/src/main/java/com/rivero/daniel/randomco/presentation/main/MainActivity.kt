package com.rivero.daniel.randomco.presentation.main

import android.os.Bundle
import android.support.v4.app.FragmentManager
import com.rivero.daniel.randomco.R
import com.rivero.daniel.randomco.infrastructure.di.component.ViewComponent
import com.rivero.daniel.randomco.presentation.base.BaseActivity
import com.rivero.daniel.randomco.presentation.main.fragment.NearUsersFragment
import com.rivero.daniel.randomco.presentation.main.fragment.UserListFragment
import com.rivero.daniel.randomco.presentation.main.model.UserListType
import com.rivero.daniel.randomco.presentation.main.presenter.MainPresenter
import com.rivero.daniel.randomco.presentation.main.view.MainView
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : BaseActivity(), MainView, FragmentManager.OnBackStackChangedListener {

    @Inject
    lateinit var presenter: MainPresenter

    private var displayHomeAsUpEnabled = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.addOnBackStackChangedListener(this)
        presenter.onAttach(this)
        initToolbar()
        initBottomNavigation()
        initFragmentContainer(R.id.fragmentContainer, UserListFragment.getInstance(UserListType.Normal()))
    }

    override fun initializeInjector(viewComponent: ViewComponent) {
        viewComponent.inject(this)
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(displayHomeAsUpEnabled)

        toolbar.setNavigationOnClickListener({ onBackPressed() })
    }

    private fun initBottomNavigation() {
        bottomNavigation.selectedItemId = R.id.item_user_list
        bottomNavigation.setOnNavigationItemSelectedListener {
            if (bottomNavigation.selectedItemId != it.itemId) {
                presenter.onClickBottomNavigationItem(it.itemId)
                return@setOnNavigationItemSelectedListener true
            }
            return@setOnNavigationItemSelectedListener false
        }
    }

    override fun onBackStackChanged() {
        presenter.backStackChanged(isRootFragment())
    }

    private fun isRootFragment(): Boolean {
        val fragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
        return fragment is UserListFragment || fragment is NearUsersFragment
    }

    override fun showHomeAsUp(show: Boolean) {
        supportActionBar?.setDisplayHomeAsUpEnabled(show)
    }

}