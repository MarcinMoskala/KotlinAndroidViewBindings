package com.marcinmoskala.kotlinandroidviewbindings_kae

import android.app.Activity
import android.support.annotation.IdRes
import android.view.View
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun Activity.bindToVisibility(@IdRes editTextId: Int): ReadWriteProperty<Any?, Boolean>
        = bindToVisibility { findViewById(editTextId) }

fun bindToVisibility(viewProvider: () -> View): ReadWriteProperty<Any?, Boolean>
        = bindToVisibility(lazy(viewProvider))

fun bindToVisibility(lazyViewProvider: Lazy<View>): ReadWriteProperty<Any?, Boolean>
        = ViewVisibilityBinding(lazyViewProvider)

private class ViewVisibilityBinding(viewProvider: Lazy<View>) : ReadWriteProperty<Any?, Boolean> {

    val view by viewProvider

    override fun getValue(thisRef: Any?, property: KProperty<*>): Boolean {
        return view.visibility == View.VISIBLE
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: Boolean) {
        view.visibility = if(value) View.VISIBLE else View.GONE
    }
}