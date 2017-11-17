package com.marcinmoskala.kotlinandroidviewbindings

import android.app.Activity
import android.app.Fragment
import android.os.Build
import android.support.annotation.IdRes
import android.support.annotation.RequiresApi
import android.widget.FrameLayout
import android.widget.TextView
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun Activity.bindToTextId(@IdRes textViewId: Int): ReadWriteProperty<Any?, Int?>
        = bindToTextId { findViewById(textViewId) as TextView }

@RequiresApi(Build.VERSION_CODES.HONEYCOMB)
fun Fragment.bindToTextId(@IdRes textViewId: Int): ReadWriteProperty<Any?, Int?>
        = bindToTextId { view.findViewById(textViewId) as TextView }

fun android.support.v4.app.Fragment.bindToTextId(@IdRes textViewId: Int): ReadWriteProperty<Any?, Int?>
        = bindToTextId { view!!.findViewById(textViewId) as TextView }

fun FrameLayout.bindToTextId(@IdRes textViewId: Int): ReadWriteProperty<Any?, Int?>
        = bindToTextId { findViewById(textViewId) as TextView }

fun Activity.bindToTextId(viewProvider: () -> TextView): ReadWriteProperty<Any?, Int?>
        = bindToTextId { viewProvider() }

@RequiresApi(Build.VERSION_CODES.HONEYCOMB)
fun Fragment.bindToTextId(viewProvider: () -> TextView): ReadWriteProperty<Any?, Int?>
        = bindToTextId { viewProvider() }

fun android.support.v4.app.Fragment.bindToTextId(viewProvider: () -> TextView): ReadWriteProperty<Any?, Int?>
        = bindToTextId { viewProvider() }

fun TextView.bindToTextId(): ReadWriteProperty<Any?, Int?>
        = bindToTextId { this }

private fun bindToTextId(viewProvider: () -> TextView): ReadWriteProperty<Any?, Int?>
        = TextIdBinding(lazy(viewProvider))

private class TextIdBinding(lazyViewProvider: Lazy<TextView>) : ReadWriteProperty<Any?, Int?> {

    val view by lazyViewProvider
    var textId: Int? = null

    override fun getValue(thisRef: Any?, property: KProperty<*>): Int? {
        return textId
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: Int?) {
        value ?: return
        textId = value
        view.setText(value)
    }
}