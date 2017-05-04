package com.marcinmoskala.kotlinandroidviewbindings

import android.app.Activity
import android.support.annotation.IdRes
import android.widget.EditText
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun Activity.bindToErrorId(@IdRes editTextId: Int): ReadWriteProperty<Any?, Int?>
        = bindToErrorId { findViewById(editTextId) as EditText }

fun bindToErrorId(viewProvider: () -> EditText): ReadWriteProperty<Any?, Int?>
        = bindToErrorId(lazy(viewProvider))

fun bindToErrorId(lazyViewProvider: Lazy<EditText>): ReadWriteProperty<Any?, Int?>
        = EditTextViewErrorIdBinding(lazyViewProvider)

private class EditTextViewErrorIdBinding(lazyViewProvider: Lazy<EditText>) : ReadWriteProperty<Any?, Int?> {

    val view by lazyViewProvider
    var currentError: Int? = null

    override fun getValue(thisRef: Any?, property: KProperty<*>): Int? {
        return currentError
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: Int?) {
        currentError = value
        view.error = value?.let { view.context.getString(value) }
    }
}