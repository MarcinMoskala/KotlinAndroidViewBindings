package com.marcinmoskala.kotlinandroidviewbindings

import android.app.Activity
import android.support.annotation.IdRes
import android.widget.TextView
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun Activity.bindToTextView(@IdRes textViewId: Int): ReadWriteProperty<Any?, String>
        = bindToTextView { findViewById(textViewId) as TextView }

fun bindToTextView(viewProvider: () -> TextView): ReadWriteProperty<Any?, String>
        = bindToTextView(lazy(viewProvider))

fun bindToTextView(lazyViewProvider: Lazy<TextView>): ReadWriteProperty<Any?, String>
        = TextViewTextBinding(lazyViewProvider)

private class TextViewTextBinding(lazyViewProvider: Lazy<TextView>) : ReadWriteProperty<Any?, String> {

    val view by lazyViewProvider

    override fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return view.text.toString()
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        view.text = value
    }
}