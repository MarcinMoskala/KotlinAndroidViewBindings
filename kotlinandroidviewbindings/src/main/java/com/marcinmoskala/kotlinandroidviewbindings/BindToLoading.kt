package com.marcinmoskala.kotlinandroidviewbindings

import android.app.Activity
import android.support.annotation.IdRes
import android.view.View
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun Activity.bindToLoading(
        @IdRes progressViewId: Int,
        @IdRes restViewHolderId: Int?
): ReadWriteProperty<Any?, Boolean> = bindToLoading(
        progressViewProvider = { findViewById(progressViewId) },
        restViewHolderProvider = { restViewHolderId?.let { findViewById(it) } }
)

private fun bindToLoading(
        progressViewProvider: () -> View,
        restViewHolderProvider: () -> View?
): ReadWriteProperty<Any?, Boolean> = LoadingBinding(
        lazy(progressViewProvider),
        lazy(restViewHolderProvider)
)

private class LoadingBinding(
        progressLazyViewProvider: Lazy<View>,
        restViewHolderLazyProvider: Lazy<View?>
) : ReadWriteProperty<Any?, Boolean> {

    val progressView by progressLazyViewProvider
    val restOfView by restViewHolderLazyProvider

    override fun getValue(thisRef: Any?, property: KProperty<*>): Boolean {
        return progressView.visibility == View.VISIBLE
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: Boolean) {
        progressView.visibility = if(value) View.VISIBLE else View.GONE
        restOfView?.visibility = if(!value) View.GONE else View.VISIBLE
    }
}