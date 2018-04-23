package com.rivero.daniel.randomco.presentation.main.fragment

import android.os.Bundle
import android.support.v7.widget.AppCompatEditText
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import com.rivero.daniel.randomco.R
import com.rivero.daniel.randomco.infrastructure.di.component.ViewComponent
import com.rivero.daniel.randomco.presentation.base.BaseFragment
import com.rivero.daniel.randomco.presentation.base.utils.isPhoneNumber
import com.rivero.daniel.randomco.presentation.base.utils.isValidEmail
import com.rivero.daniel.randomco.presentation.base.utils.validate
import com.rivero.daniel.randomco.presentation.main.presenter.RegisterUserPresenter
import com.rivero.daniel.randomco.presentation.main.view.RegisterUserView
import kotlinx.android.synthetic.main.fragment_register_user.*
import javax.inject.Inject


class RegisterUserFragment : BaseFragment(), RegisterUserView {

    @Inject
    lateinit var presenter: RegisterUserPresenter

    companion object {
        fun getInstance(): RegisterUserFragment {
            return RegisterUserFragment()
        }
    }

    override val layoutContainer: Int
        get() = R.layout.fragment_register_user

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
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_register_user, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        menu?.findItem(R.id.action_add)?.isVisible = false
        super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when (item?.itemId) {
        R.id.action_confirm -> {
            validateForm()
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun validateForm(): Boolean {
        if (isValidForm()) {
            presenter.onClickConfirm()
            return true
        }
        return false
    }

    private fun isValidForm(): Boolean {
        textName.validate({ it.isNotEmpty() }, getString(R.string.validation_empty_error_message))
        textSurname.validate({ it.isNotEmpty() }, getString(R.string.validation_empty_error_message))
        textUsername.validate({ it.isNotEmpty() }, getString(R.string.validation_empty_error_message))
        textEmail.validate({it.isValidEmail()}, getString(R.string.validation_email_error_message))
        textPhone.validate({it.isPhoneNumber()}, getString(R.string.validation_phone_error_message))
        textStreet.validate({ it.isNotEmpty() }, getString(R.string.validation_empty_error_message))
        textCity.validate({ it.isNotEmpty() }, getString(R.string.validation_empty_error_message))
        textState.validate({ it.isNotEmpty() }, getString(R.string.validation_empty_error_message))

        return !hasErrors()
    }

    private fun hasErrors(): Boolean {
        for (index in 0..layoutForm.childCount) {
            val child = layoutForm.getChildAt(index)
            if (child is AppCompatEditText && !child.error.isNullOrEmpty()) {
                return true
            }
        }
        return false
    }


    override fun getName(): String {
        return textName.text.toString()
    }

    override fun getSurname(): String {
        return textSurname.text.toString()
    }

    override fun getUsername(): String {
        return textUsername.text.toString()
    }

    override fun getGender(): String {
        return textGender.selectedItem.toString()
    }

    override fun getEmail(): String {
        return textEmail.text.toString()
    }

    override fun getPhone(): String {
        return textPhone.text.toString()
    }

    override fun getStreet(): String {
        return textStreet.text.toString()
    }

    override fun getCity(): String {
        return textCity.text.toString()
    }

    override fun getState(): String {
        return textState.text.toString()
    }

}