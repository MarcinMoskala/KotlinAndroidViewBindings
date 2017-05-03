package com.marcinmoskala.kotlinandroidviewbindings_kae

import android.app.Activity
import android.support.annotation.IdRes
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

fun Activity.bindToRequestFocus(@IdRes editViewId: Int): ReadOnlyProperty<Any?, () -> Unit>
        = bindToRequestFocus { findViewById(editViewId) }

fun AppCompatActivity.bindToRequestFocus(@IdRes editViewId: Int): ReadOnlyProperty<Any?, () -> Unit>
        = bindToRequestFocus { findViewById(editViewId) }

fun bindToRequestFocus(viewProvider: () -> View): ReadOnlyProperty<Any?, () -> Unit>
        = bindToRequestFocus(lazy(viewProvider))

fun bindToRequestFocus(lazyViewProvider: Lazy<View>): ReadOnlyProperty<Any?, () -> Unit>
        = RequestFocusBinding(lazyViewProvider)

private class RequestFocusBinding(viewProvider: Lazy<View>) : ReadOnlyProperty<Any?, () -> Unit> {

    val view by viewProvider
    val requestFocus: ()->Unit by lazy { fun() { view.requestFocus() } }

    override fun getValue(thisRef: Any?, property: KProperty<*>) = requestFocus
}