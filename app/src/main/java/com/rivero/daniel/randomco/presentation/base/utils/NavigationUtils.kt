package com.rivero.daniel.randomco.presentation.base.utils

import android.content.Intent
import android.os.Bundle
import com.rivero.daniel.randomco.presentation.base.BaseActivity
import com.rivero.daniel.randomco.presentation.base.BaseFragment
import java.io.Serializable


//region BaseActivity extensions
inline fun <reified T : Serializable> Intent.setParamsByClass(obj: T, tag: String = "") {
    putExtra(T::class.java.name + tag, obj)
}

inline fun <reified T : Serializable> BaseActivity.getParamsByClass(tag: String = ""): T =
        intent.extras.get(T::class.java.name + tag) as T
//endregion

//region BaseFragment extensions
inline fun <reified T : Serializable> BaseFragment.setParamsByClass(obj: T, tag: String = "") {
    if (arguments == null) {
        arguments = Bundle()
    }
    arguments?.putSerializable(T::class.java.name + tag, obj)
}

inline fun <reified T : Serializable> BaseFragment.getParamsByClass(tag: String = ""): T =
        arguments?.getSerializable(T::class.java.name + tag) as T
//endregion