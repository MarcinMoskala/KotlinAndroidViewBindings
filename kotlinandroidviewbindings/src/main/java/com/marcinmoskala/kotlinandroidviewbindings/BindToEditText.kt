package com.marcinmoskala.kotlinandroidviewbindings

import android.app.Activity
import android.support.annotation.IdRes
import android.widget.EditText
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun Activity.bindToEditText(@IdRes editTextId: Int): ReadWriteProperty<Any?, String>
        = bindToEditText { findViewById(editTextId) as EditText }

private fun bindToEditText(viewProvider: () -> EditText): ReadWriteProperty<Any?, String>
        = EditTextViewTextBinding(lazy(viewProvider))

private class EditTextViewTextBinding(lazyViewProvider: Lazy<EditText>) : ReadWriteProperty<Any?, String> {

    val view by lazyViewProvider

    override fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return view.text.toString()
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        view.setText(value)
    }
}