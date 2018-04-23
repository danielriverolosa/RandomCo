package com.rivero.daniel.randomco.presentation.main.presenter

import com.rivero.daniel.randomco.R
import com.rivero.daniel.randomco.presentation.base.Navigator
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainPresenterTest {

    @InjectMocks
    private lateinit var presenter: MainPresenter

    @Mock
    private lateinit var navigator: Navigator

    @Test
    @Throws(Exception::class)
    fun onClickBottomNavigationItem_whenClickUserList_shouldNavigateCorrectly() {
        val menuItemId = R.id.item_user_list

        presenter.onClickBottomNavigationItem(menuItemId)

        verify<Navigator>(navigator).showUserList()
    }

    @Test
    @Throws(Exception::class)
    fun onClickBottomNavigationItem_whenClickFavoriteList_shouldNavigateCorrectly() {
        val menuItemId = R.id.item_favorite_list

        presenter.onClickBottomNavigationItem(menuItemId)

        verify<Navigator>(navigator).showFavoriteList()
    }


    @Test
    @Throws(Exception::class)
    fun onClickBottomNavigationItem_whenClickNearMeList_shouldNavigateCorrectly() {
        val menuItemId = R.id.item_near_list

        presenter.onClickBottomNavigationItem(menuItemId)

        verify<Navigator>(navigator).showNearMeList()
    }

}