package com.marcinmoskala.kotlinandroidviewbindings

import android.app.Activity
import android.support.annotation.IdRes
import android.view.View
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun Activity.bindToLongClick(@IdRes viewId: Int): ReadWriteProperty<Any?, () -> Unit>
        = bindToLongClick { findViewById(viewId) }

fun bindToLongClick(viewProvider: () -> View): ReadWriteProperty<Any?, () -> Unit>
        = bindToLongClick(lazy(viewProvider))

fun bindToLongClick(lazyViewProvider: Lazy<View>): ReadWriteProperty<Any?, () -> Unit>
        = OnLongClickBinding(lazyViewProvider)

private class OnLongClickBinding(viewProvider: Lazy<View>) : ReadWriteProperty<Any?, () -> Unit> {

    val view by viewProvider
    var function: (() -> Unit)? = null

    override fun getValue(thisRef: Any?, property: KProperty<*>): () -> Unit {
        return function ?: noop
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: () -> Unit) {
        function = value
        view.setOnLongClickListener { function?.invoke(); true }
    }

    companion object {
        val noop: () -> Unit = {}
    }
}