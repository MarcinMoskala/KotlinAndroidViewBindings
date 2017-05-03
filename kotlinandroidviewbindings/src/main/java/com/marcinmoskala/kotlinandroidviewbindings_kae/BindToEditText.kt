package com.marcinmoskala.kotlinandroidviewbindings_kae

import android.app.Activity
import android.support.annotation.IdRes
import android.support.v7.app.AppCompatActivity
import android.widget.EditText
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun Activity.bindToEditText(@IdRes editTextId: Int): ReadWriteProperty<Any?, String>
        = bindToEditText { findViewById(editTextId) as EditText }

fun AppCompatActivity.bindToEditText(@IdRes editTextId: Int): ReadWriteProperty<Any?, String>
        = bindToEditText { findViewById(editTextId) as EditText }

fun bindToEditText(viewProvider: () -> EditText): ReadWriteProperty<Any?, String>
        = bindToEditText(lazy(viewProvider))

fun bindToEditText(lazyViewProvider: Lazy<EditText>): ReadWriteProperty<Any?, String>
        = EditTextViewTextBinding(lazyViewProvider)

private class EditTextViewTextBinding(lazyViewProvider: Lazy<EditText>) : ReadWriteProperty<Any?, String> {

    val view by lazyViewProvider

    override fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return view.text.toString()
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        view.setText(value)
    }
}