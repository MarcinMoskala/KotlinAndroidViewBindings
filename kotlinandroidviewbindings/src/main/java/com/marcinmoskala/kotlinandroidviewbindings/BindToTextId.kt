package com.marcinmoskala.kotlinandroidviewbindings

import android.app.Activity
import android.support.annotation.IdRes
import android.widget.TextView
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun Activity.bindToTextId(@IdRes textViewId: Int): ReadWriteProperty<Any?, Int?>
        = bindToTextId { findViewById(textViewId) as TextView }

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