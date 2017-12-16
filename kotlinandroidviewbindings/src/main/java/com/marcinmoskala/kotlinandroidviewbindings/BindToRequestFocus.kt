package com.marcinmoskala.kotlinandroidviewbindings

import android.app.Activity
import android.app.Fragment
import android.os.Build
import android.support.annotation.IdRes
import android.support.annotation.RequiresApi
import android.view.View
import android.widget.FrameLayout
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

fun Activity.bindToRequestFocus(@IdRes editViewId: Int): ReadOnlyProperty<Any?, () -> Unit>
        = bindToRequestFocusP { findViewById(editViewId) }

@RequiresApi(Build.VERSION_CODES.HONEYCOMB)
fun Fragment.bindToRequestFocus(@IdRes editViewId: Int): ReadOnlyProperty<Any?, () -> Unit>
        = bindToRequestFocusP { view.findViewById(editViewId) }

fun android.support.v4.app.Fragment.bindToRequestFocus(@IdRes editViewId: Int): ReadOnlyProperty<Any?, () -> Unit>
        = bindToRequestFocusP { view!!.findViewById(editViewId) }

fun FrameLayout.bindToRequestFocus(@IdRes editViewId: Int): ReadOnlyProperty<Any?, () -> Unit>
        = bindToRequestFocusP { findViewById(editViewId) }

fun Activity.bindToRequestFocus(viewProvider: () -> View): ReadOnlyProperty<Any?, () -> Unit>
        = bindToRequestFocusP { viewProvider() }

@RequiresApi(Build.VERSION_CODES.HONEYCOMB)
fun Fragment.bindToRequestFocus(viewProvider: () -> View): ReadOnlyProperty<Any?, () -> Unit>
        = bindToRequestFocusP { viewProvider() }

fun android.support.v4.app.Fragment.bindToRequestFocus(viewProvider: () -> View): ReadOnlyProperty<Any?, () -> Unit>
        = bindToRequestFocusP { viewProvider() }

fun View.bindToRequestFocus(): ReadOnlyProperty<Any?, () -> Unit>
        = bindToRequestFocusP { this }

private fun bindToRequestFocusP(viewProvider: () -> View): ReadOnlyProperty<Any?, () -> Unit>
        = RequestFocusBinding(lazy(viewProvider))

private class RequestFocusBinding(viewProvider: Lazy<View>) : ReadOnlyProperty<Any?, () -> Unit> {

    val view by viewProvider
    val requestFocus: ()->Unit by lazy { fun() { view.requestFocus() } }

    override fun getValue(thisRef: Any?, property: KProperty<*>) = requestFocus
}