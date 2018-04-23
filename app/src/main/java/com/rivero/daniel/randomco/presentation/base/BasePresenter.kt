package com.rivero.daniel.randomco.presentation.base

import javax.inject.Inject


abstract class BasePresenter<T: BaseView> {

    @Inject
    lateinit var navigator: Navigator

    lateinit var view: T

    open fun onAttach(view: T) {
        this.view = view
    }
}