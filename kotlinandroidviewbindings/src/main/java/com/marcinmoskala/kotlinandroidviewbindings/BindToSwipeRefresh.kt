package com.marcinmoskala.kotlinandroidviewbindings

import android.app.Activity
import android.support.annotation.IdRes
import android.support.v4.widget.SwipeRefreshLayout
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun Activity.bindToSwipeRefresh(@IdRes swipeRefreshLayoutId: Int): ReadOnlyProperty<Any?, Boolean>
        = bindToSwipeRefresh { findViewById(swipeRefreshLayoutId) as SwipeRefreshLayout }

fun bindToSwipeRefresh(viewProvider: () -> SwipeRefreshLayout): ReadOnlyProperty<Any?, Boolean>
        = bindToSwipeRefresh(lazy(viewProvider))

private fun bindToSwipeRefresh(lazyViewProvider: Lazy<SwipeRefreshLayout>): ReadOnlyProperty<Any?, Boolean>
        = bindToSwipeRefresh(lazyViewProvider)

private class SwipeRefreshBinding(viewProvider: Lazy<SwipeRefreshLayout>) : ReadWriteProperty<Any?, Boolean> {

    val view by viewProvider

    override fun getValue(thisRef: Any?, property: KProperty<*>): Boolean {
        return view.isRefreshing
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: Boolean) {
        view.isRefreshing = value
    }
}