package com.marcinmoskala.kotlinandroidviewbindings

import android.app.Activity
import android.app.Fragment
import android.os.Build
import android.support.annotation.IdRes
import android.support.annotation.RequiresApi
import android.widget.EditText
import android.widget.FrameLayout
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun Activity.bindToErrorId(@IdRes editTextId: Int): ReadWriteProperty<Any?, Int?>
        = bindToErrorId { findViewById(editTextId) as EditText }

@RequiresApi(Build.VERSION_CODES.HONEYCOMB)
fun Fragment.bindToErrorId(@IdRes viewId: Int): ReadWriteProperty<Any?, Int?>
        = bindToErrorId { view.findViewById(viewId) as EditText }

fun android.support.v4.app.Fragment.bindToErrorId(@IdRes viewId: Int): ReadWriteProperty<Any?, Int?>
        = bindToErrorId { view!!.findViewById(viewId) as EditText }

fun FrameLayout.bindToErrorId(@IdRes viewId: Int): ReadWriteProperty<Any?, Int?>
        = bindToErrorId { findViewById(viewId) as EditText }

fun Activity.bindToErrorId(viewProvider: () -> EditText): ReadWriteProperty<Any?, Int?>
        = bindToErrorId { viewProvider() }

@RequiresApi(Build.VERSION_CODES.HONEYCOMB)
fun Fragment.bindToErrorId(viewProvider: () -> EditText): ReadWriteProperty<Any?, Int?>
        = bindToErrorId { viewProvider() }

fun android.support.v4.app.Fragment.bindToErrorId(viewProvider: () -> EditText): ReadWriteProperty<Any?, Int?>
        = bindToErrorId { viewProvider() }

fun EditText.bindToErrorId(): ReadWriteProperty<Any?, Int?>
        = bindToErrorId { this }

private fun bindToErrorId(viewProvider: () -> EditText): ReadWriteProperty<Any?, Int?>
        = EditTextViewErrorIdBinding(lazy(viewProvider))

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