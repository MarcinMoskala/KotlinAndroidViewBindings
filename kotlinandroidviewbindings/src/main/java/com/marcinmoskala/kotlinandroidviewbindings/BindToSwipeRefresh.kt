package com.marcinmoskala.kotlinandroidviewbindings

import android.app.Activity
import android.app.Fragment
import android.os.Build
import android.support.annotation.IdRes
import android.support.annotation.RequiresApi
import android.support.v4.widget.SwipeRefreshLayout
import android.widget.FrameLayout
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun Activity.bindToSwipeRefresh(@IdRes swipeRefreshLayoutId: Int): ReadWriteProperty<Any?, Boolean>
        = bindToSwipeRefresh { findViewById(swipeRefreshLayoutId) as SwipeRefreshLayout }

@RequiresApi(Build.VERSION_CODES.HONEYCOMB)
fun Fragment.bindToSwipeRefresh(@IdRes swipeRefreshLayoutId: Int): ReadWriteProperty<Any?, Boolean>
        = bindToSwipeRefresh { view.findViewById(swipeRefreshLayoutId) as SwipeRefreshLayout }

fun FrameLayout.bindToSwipeRefresh(@IdRes swipeRefreshLayoutId: Int): ReadWriteProperty<Any?, Boolean>
        = bindToSwipeRefresh { findViewById(swipeRefreshLayoutId) as SwipeRefreshLayout }

fun SwipeRefreshLayout.bindToSwipeRefresh(): ReadWriteProperty<Any?, Boolean>
        = bindToSwipeRefresh { this }

fun bindToSwipeRefresh(viewProvider: () -> SwipeRefreshLayout): ReadWriteProperty<Any?, Boolean>
        = SwipeRefreshBinding(lazy(viewProvider))

private class SwipeRefreshBinding(viewProvider: Lazy<SwipeRefreshLayout>) : ReadWriteProperty<Any?, Boolean> {

    val view by viewProvider

    override fun getValue(thisRef: Any?, property: KProperty<*>): Boolean {
        return view.isRefreshing
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: Boolean) {
        view.isRefreshing = value
    }
}