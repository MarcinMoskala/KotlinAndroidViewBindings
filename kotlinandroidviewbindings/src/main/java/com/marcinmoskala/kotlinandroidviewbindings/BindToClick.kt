package com.marcinmoskala.kotlinandroidviewbindings

import android.app.Activity
import android.app.Fragment
import android.os.Build
import android.support.annotation.IdRes
import android.support.annotation.RequiresApi
import android.view.View
import android.widget.FrameLayout
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun Activity.bindToClick(@IdRes viewId: Int): ReadWriteProperty<Any?, () -> Unit>
        = bindToClickP { findViewById(viewId) }

@RequiresApi(Build.VERSION_CODES.HONEYCOMB)
fun Fragment.bindToClick(@IdRes viewId: Int): ReadWriteProperty<Any?, () -> Unit>
        = bindToClickP { view.findViewById(viewId) }

fun android.support.v4.app.Fragment.bindToClick(@IdRes viewId: Int): ReadWriteProperty<Any?, () -> Unit>
        = bindToClickP { view!!.findViewById(viewId) }

fun FrameLayout.bindToClick(@IdRes viewId: Int): ReadWriteProperty<Any?, () -> Unit>
        = bindToClickP { findViewById(viewId) }

fun Activity.bindToClick(viewProvider: ()->View): ReadWriteProperty<Any?, () -> Unit>
        = bindToClickP { viewProvider() }

@RequiresApi(Build.VERSION_CODES.HONEYCOMB)
fun Fragment.bindToClick(viewProvider: ()->View): ReadWriteProperty<Any?, () -> Unit>
        = bindToClickP { viewProvider() }

fun android.support.v4.app.Fragment.bindToClick(viewProvider: ()->View): ReadWriteProperty<Any?, () -> Unit>
        = bindToClickP { viewProvider() }

fun View.bindToClick(): ReadWriteProperty<Any?, () -> Unit>
        = bindToClickP { this }

private fun bindToClickP(viewProvider: () -> View): ReadWriteProperty<Any?, () -> Unit>
        = OnClickBinding(lazy(viewProvider))

private class OnClickBinding(viewProvider: Lazy<View>) : ReadWriteProperty<Any?, () -> Unit> {

    val view by viewProvider
    var function: (() -> Unit)? = null

    override fun getValue(thisRef: Any?, property: KProperty<*>): () -> Unit {
        return function ?: noop
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: () -> Unit) {
        setUpListener()
        function = value
    }

    fun setUpListener() {
        if (function == null) view.setOnClickListener { function?.invoke() }
    }

    companion object {
        val noop: () -> Unit = {}
    }
}