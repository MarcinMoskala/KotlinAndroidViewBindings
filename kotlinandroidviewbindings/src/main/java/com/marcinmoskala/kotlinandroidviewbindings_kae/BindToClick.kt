package com.marcinmoskala.kotlinandroidviewbindings_kae

import android.app.Activity
import android.support.annotation.IdRes
import android.view.View
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun Activity.bindToClick(@IdRes viewId: Int): ReadWriteProperty<Any?, () -> Unit>
        = bindToClick { findViewById(viewId) }

fun bindToClick(viewProvider: () -> View): ReadWriteProperty<Any?, () -> Unit>
        = bindToClick(lazy(viewProvider))

fun bindToClick(lazyViewProvider: Lazy<View>): ReadWriteProperty<Any?, () -> Unit>
        = OnClickBinding(lazyViewProvider)

private class OnClickBinding(viewProvider: Lazy<View>) : ReadWriteProperty<Any?, () -> Unit> {

    val view by viewProvider
    var function: (() -> Unit)? = null

    override fun getValue(thisRef: Any?, property: KProperty<*>): () -> Unit {
        return function ?: noop
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: () -> Unit) {
        function = value
        view.setOnClickListener { function?.invoke() }
    }

    companion object {
        val noop: () -> Unit = {}
    }
}