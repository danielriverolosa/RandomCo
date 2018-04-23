package com.rivero.daniel.randomco.presentation.base.utils

import android.content.Context
import android.support.v7.widget.AppCompatEditText
import android.support.v7.widget.SearchView
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.rivero.daniel.randomco.R

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun AppCompatEditText.afterTextChanged(afterTextChanged: ((String) -> Unit)?) {
    this.addTextChangedListener(object: TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            afterTextChanged?.invoke(s.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
    })
}

fun AppCompatEditText.validate(validator: (String) -> Boolean, message: String) {
    this.afterTextChanged {
        this.error = if (validator(it)) null else message
    }

    this.error = if (validator(this.text.toString())) null else message
}

fun SearchView.onQueryTextChange(queryTextChange: (String) -> Unit) {
    this.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
        override fun onQueryTextChange(newText: String?): Boolean {
            newText?.let {
                queryTextChange.invoke(newText)
            }
            return true
        }

        override fun onQueryTextSubmit(query: String?): Boolean { return false }
    })
}

fun ImageView.showCircleImage(context: Context, url: String?) {
    Glide.with(context)
            .load(url)
            .apply(RequestOptions
                    .bitmapTransform(CircleCrop())
                    .placeholder(R.drawable.ic_account_large))
            .into(this)
}