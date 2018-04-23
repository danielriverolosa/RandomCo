package com.rivero.daniel.randomco.presentation.base

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import com.rivero.daniel.randomco.R
import com.rivero.daniel.randomco.domain.User
import com.rivero.daniel.randomco.presentation.main.MainActivity
import com.rivero.daniel.randomco.presentation.main.fragment.NearUsersFragment
import com.rivero.daniel.randomco.presentation.main.fragment.RegisterUserFragment
import com.rivero.daniel.randomco.presentation.main.fragment.UserDetailFragment
import com.rivero.daniel.randomco.presentation.main.fragment.UserListFragment
import com.rivero.daniel.randomco.presentation.main.model.UserListType
import javax.inject.Inject


class Navigator @Inject constructor(private val context: Context) {

    private val fragmentManager: FragmentManager
        get() = (context as AppCompatActivity).supportFragmentManager

    private fun showFragment(containerId: Int, fragment: Fragment, backEnable: Boolean) {
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(containerId, fragment)

        if (backEnable) {
            val fragmentTag = retrieveFragmentTag(fragment)
            transaction.addToBackStack(fragmentTag)
        }

        transaction.commit()
    }

    private fun retrieveFragmentTag(fragment: Fragment?): String? {
        return fragment?.javaClass?.simpleName
    }

    fun showUserList() {
        showFragment(R.id.fragmentContainer, UserListFragment.getInstance(UserListType.Normal()), false)
    }

    fun showFavoriteList() {
        showFragment(R.id.fragmentContainer, UserListFragment.getInstance(UserListType.Favorite()), false)
    }

    fun showNearMeList() {
        showFragment(R.id.fragmentContainer, NearUsersFragment.getInstance(), false)
    }

    fun showUserDetail(user: User) {
        showFragment(R.id.fragmentContainer, UserDetailFragment.getInstance(user), true)
    }

    fun showRegisterUser() {
        showFragment(R.id.fragmentContainer, RegisterUserFragment.getInstance(), true)
    }

}