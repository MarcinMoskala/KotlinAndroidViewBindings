package com.marcinmoskala.kotlinandroidviewbindings

import android.app.Activity
import android.support.annotation.IdRes
import android.widget.TextView
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun Activity.bindToText(@IdRes textViewId: Int): ReadWriteProperty<Any?, String>
        = bindToText { findViewById(textViewId) as TextView }

private fun bindToText(viewProvider: () -> TextView): ReadWriteProperty<Any?, String>
        = TextViewTextBinding(lazy(viewProvider))

private class TextViewTextBinding(lazyViewProvider: Lazy<TextView>) : ReadWriteProperty<Any?, String> {

    val view by lazyViewProvider

    override fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return view.text.toString()
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        view.text = value
    }
}