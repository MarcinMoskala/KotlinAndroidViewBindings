package com.marcinmoskala.kotlinandroidviewbindings

import android.app.Activity
import android.support.annotation.IdRes
import android.view.View
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun Activity.bindToClick(@IdRes viewId: Int): ReadWriteProperty<Any?, () -> Unit>
        = bindToClick { findViewById(viewId) }

private fun bindToClick(viewProvider: () -> View): ReadWriteProperty<Any?, () -> Unit>
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
        if(function == null) view.setOnClickListener { function?.invoke() }
    }

    companion object {
        val noop: () -> Unit = {}
    }
}