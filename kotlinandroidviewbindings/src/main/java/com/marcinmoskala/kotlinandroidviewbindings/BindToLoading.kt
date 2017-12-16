package com.marcinmoskala.kotlinandroidviewbindings

import android.app.Activity
import android.app.Fragment
import android.os.Build
import android.support.annotation.IdRes
import android.support.annotation.RequiresApi
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun Activity.bindToLoading(
        @IdRes progressViewId: Int,
        @IdRes restViewHolderId: Int
): ReadWriteProperty<Any?, Boolean> = bindToLoadingP(
        progressViewProvider = { findViewById(progressViewId) },
        restViewHolderProvider = { findViewById(restViewHolderId) }
)

@RequiresApi(Build.VERSION_CODES.HONEYCOMB)
fun Fragment.bindToLoading(
        @IdRes progressViewId: Int,
        @IdRes restViewHolderId: Int
): ReadWriteProperty<Any?, Boolean> = bindToLoadingP(
        progressViewProvider = { view.findViewById(progressViewId) },
        restViewHolderProvider = { view.findViewById(restViewHolderId) }
)

@RequiresApi(Build.VERSION_CODES.HONEYCOMB)
fun android.support.v4.app.Fragment.bindToLoading(
        @IdRes progressViewId: Int,
        @IdRes restViewHolderId: Int
): ReadWriteProperty<Any?, Boolean> = bindToLoadingP(
        progressViewProvider = { view!!.findViewById(progressViewId) },
        restViewHolderProvider = { view!!.findViewById(restViewHolderId) }
)

fun FrameLayout.bindToLoading(
        @IdRes progressViewId: Int,
        @IdRes restViewHolderId: Int
): ReadWriteProperty<Any?, Boolean> = bindToLoadingP(
        progressViewProvider = { findViewById(progressViewId) },
        restViewHolderProvider = { findViewById(restViewHolderId) }
)

fun Activity.bindToLoading(
        progressViewProvider: () -> View,
        restViewProvider: () -> View
): ReadWriteProperty<Any?, Boolean> = bindToLoadingP(
        progressViewProvider = { progressViewProvider() },
        restViewHolderProvider = { restViewProvider() }
)

@RequiresApi(Build.VERSION_CODES.HONEYCOMB)
fun Fragment.bindToLoading(
        progressViewProvider: () -> View,
        restViewProvider: () -> View
): ReadWriteProperty<Any?, Boolean> = bindToLoadingP(
        progressViewProvider = { progressViewProvider() },
        restViewHolderProvider = { restViewProvider() }
)

@RequiresApi(Build.VERSION_CODES.HONEYCOMB)
fun android.support.v4.app.Fragment.bindToLoading(
        progressViewProvider: () -> View,
        restViewProvider: () -> View
): ReadWriteProperty<Any?, Boolean> = bindToLoadingP(
        progressViewProvider = { progressViewProvider() },
        restViewHolderProvider = { restViewProvider() }
)

fun Pair<View, View>.bindToLoading(): ReadWriteProperty<Any?, Boolean> = bindToLoadingP(
        progressViewProvider = { first },
        restViewHolderProvider = { second }
)

private fun bindToLoadingP(
        progressViewProvider: () -> View,
        restViewHolderProvider: () -> View
): ReadWriteProperty<Any?, Boolean> = LoadingBinding(
        lazy(progressViewProvider),
        lazy(restViewHolderProvider)
)

private class LoadingBinding(
        progressLazyViewProvider: Lazy<View>,
        restViewHolderLazyProvider: Lazy<View>
) : ReadWriteProperty<Any?, Boolean> {

    val progressView by progressLazyViewProvider
    val restOfView by restViewHolderLazyProvider

    override fun getValue(thisRef: Any?, property: KProperty<*>): Boolean {
        return progressView.visibility == View.VISIBLE
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: Boolean) {
        progressView.visibility = if (value) View.VISIBLE else View.GONE
        restOfView?.visibility = if (value) View.GONE else View.VISIBLE
    }
}