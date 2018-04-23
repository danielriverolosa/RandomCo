package com.rivero.daniel.randomco.presentation.main.presenter

import com.rivero.daniel.randomco.R
import com.rivero.daniel.randomco.presentation.base.BasePresenter
import com.rivero.daniel.randomco.presentation.main.view.MainView
import javax.inject.Inject


class MainPresenter @Inject constructor(): BasePresenter<MainView>() {

    fun onClickBottomNavigationItem(menuItem: Int) {
        when(menuItem) {
            R.id.item_user_list -> navigator.showUserList()
            R.id.item_favorite_list -> navigator.showFavoriteList()
            R.id.item_near_list -> navigator.showNearMeList()
        }
    }

    fun backStackChanged(isRootFragment: Boolean) {
        view.showHomeAsUp(!isRootFragment)
    }

}