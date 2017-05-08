package com.marcinmoskala.kotlinandroidviewbindings

import android.app.Activity
import android.support.annotation.IdRes
import android.widget.TextView
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun Activity.bindToText(@IdRes textViewId: Int): ReadWriteProperty<Any?, String>
        = bindToText { findViewById(textViewId) as TextView }

fun TextView.bindToText(): ReadWriteProperty<Any?, String>
        = bindToText { this }

fun bindToText(viewProvider: () -> TextView): ReadWriteProperty<Any?, String>
        = TextBinding(lazy(viewProvider))

private class TextBinding(lazyViewProvider: Lazy<TextView>) : ReadWriteProperty<Any?, String> {

    val view by lazyViewProvider

    override fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return view.text.toString()
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        view.text = value
    }
}