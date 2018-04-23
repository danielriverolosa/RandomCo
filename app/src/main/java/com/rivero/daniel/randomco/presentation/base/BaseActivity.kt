package com.rivero.daniel.randomco.presentation.base

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.rivero.daniel.randomco.infrastructure.AndroidApplication
import com.rivero.daniel.randomco.infrastructure.di.component.ViewComponent
import com.rivero.daniel.randomco.infrastructure.di.module.ViewModule


abstract class BaseActivity : AppCompatActivity(), BaseView {

    val activityComponent by lazy {
        (application as AndroidApplication)
                .applicationComponent
                .viewComponent(ViewModule(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeInjector(activityComponent)
    }

    protected abstract fun initializeInjector(viewComponent: ViewComponent)

    protected fun initFragmentContainer(containerId: Int, fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(containerId, fragment)
        transaction.commit()
    }

    override fun close() {
        finish()
    }

    override fun showMessage(message: String) {
        val view = findViewById<View>(android.R.id.content)
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
    }

}