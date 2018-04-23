package com.rivero.daniel.randomco.presentation.main.fragment

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import com.rivero.daniel.randomco.R
import com.rivero.daniel.randomco.domain.Location
import com.rivero.daniel.randomco.domain.User
import com.rivero.daniel.randomco.infrastructure.di.component.ViewComponent
import com.rivero.daniel.randomco.presentation.base.BaseFragment
import com.rivero.daniel.randomco.presentation.base.utils.formatDate
import com.rivero.daniel.randomco.presentation.base.utils.getParamsByClass
import com.rivero.daniel.randomco.presentation.base.utils.setParamsByClass
import com.rivero.daniel.randomco.presentation.base.utils.showCircleImage
import com.rivero.daniel.randomco.presentation.main.presenter.UserDetailPresenter
import com.rivero.daniel.randomco.presentation.main.view.UserDetailView
import kotlinx.android.synthetic.main.fragment_user_detail.*
import javax.inject.Inject


class UserDetailFragment: BaseFragment(), UserDetailView {

    @Inject
    lateinit var presenter: UserDetailPresenter

    companion object {
        fun getInstance(user: User): UserDetailFragment {
            val fragment = UserDetailFragment()
            fragment.setParamsByClass(user, User::class.java.simpleName)
            return fragment
        }
    }

    override val layoutContainer: Int
        get() = R.layout.fragment_user_detail

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
        presenter.loadData(getParamsByClass(User::class.java.simpleName))
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_detail_user, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when (item?.itemId) {
        R.id.action_delete -> {
            presenter.deleteUser()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun setImageProfile(image: String?) {
        context?.let {
            imageProfile.showCircleImage(it, image)
        }
    }

    override fun fillData(user: User) {
        textTitleName.text = user.getCompleteName()
        textUsername.text = user.username
        textGender.text = user.gender.capitalize()
        textPhone.text = user.phone
        textEmail.text = user.email
        textRegistered.text = user.registered.formatDate()
        textLocation.text = getLocationFormatted(user.location)
    }

    private fun getLocationFormatted(location: Location): String {
        return getString(
                R.string.user_detail_location_text,
                location.street,
                location.city.capitalize(),
                location.state.capitalize())
    }

}