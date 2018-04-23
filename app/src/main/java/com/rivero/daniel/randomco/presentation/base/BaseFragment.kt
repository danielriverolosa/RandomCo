package com.rivero.daniel.randomco.presentation.base

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rivero.daniel.randomco.infrastructure.AndroidApplication
import com.rivero.daniel.randomco.infrastructure.di.component.ViewComponent
import com.rivero.daniel.randomco.infrastructure.di.module.ViewModule


abstract class BaseFragment : Fragment(), BaseView {

    @get:LayoutRes
    protected abstract val layoutContainer: Int

    private val fragmentComponent by lazy {
        (activity?.application as AndroidApplication)
                .applicationComponent
                .viewComponent(ViewModule(activity as BaseActivity))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeInjector(fragmentComponent)

    }

    protected abstract fun initializeInjector(viewComponent: ViewComponent)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutContainer, container, false)
    }

    override fun close() {
        activity?.supportFragmentManager?.popBackStackImmediate()
    }

    override fun showMessage(message: String) {
        (activity as BaseActivity).showMessage(message)
    }

}