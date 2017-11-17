package com.marcinmoskala.kotlinandroidviewbindings

import android.app.Activity
import android.app.Fragment
import android.os.Build
import android.support.annotation.IdRes
import android.support.annotation.RequiresApi
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.FrameLayout
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun Activity.bindToEditorActions(@IdRes editTextId: Int, predicate: (Int?, Int?) -> Boolean): ReadWriteProperty<Any?, () -> Unit>
        = bindToEditorActions(predicate, { findViewById(editTextId) as EditText })

@RequiresApi(Build.VERSION_CODES.HONEYCOMB)
fun Fragment.bindToEditorActions(@IdRes viewId: Int, predicate: (Int?, Int?) -> Boolean): ReadWriteProperty<Any?, () -> Unit>
        = bindToEditorActions(predicate) { view.findViewById(viewId) as EditText }

fun android.support.v4.app.Fragment.bindToEditorActions(@IdRes viewId: Int, predicate: (Int?, Int?) -> Boolean): ReadWriteProperty<Any?, () -> Unit>
        = bindToEditorActions(predicate) { view!!.findViewById(viewId) as EditText }

fun FrameLayout.bindToEditorActions(@IdRes viewId: Int, predicate: (Int?, Int?) -> Boolean): ReadWriteProperty<Any?, () -> Unit>
        = bindToEditorActions(predicate) { findViewById(viewId) as EditText }

fun Activity.bindToEditorActions(viewProvider: ()->EditText, predicate: (Int?, Int?) -> Boolean): ReadWriteProperty<Any?, () -> Unit>
        = bindToEditorActions(predicate, { viewProvider() })

@RequiresApi(Build.VERSION_CODES.HONEYCOMB)
fun Fragment.bindToEditorActions(viewProvider: ()->EditText, predicate: (Int?, Int?) -> Boolean): ReadWriteProperty<Any?, () -> Unit>
        = bindToEditorActions(predicate) { viewProvider() }

fun android.support.v4.app.Fragment.bindToEditorActions(viewProvider: ()->EditText, predicate: (Int?, Int?) -> Boolean): ReadWriteProperty<Any?, () -> Unit>
        = bindToEditorActions(predicate) { viewProvider() }

fun EditText.bindToEditorActions(predicate: (Int?, Int?) -> Boolean): ReadWriteProperty<Any?, () -> Unit>
        = bindToEditorActions(predicate) { this }

private fun bindToEditorActions(predicate: (Int?, Int?) -> Boolean, viewProvider: () -> EditText): ReadWriteProperty<Any?, () -> Unit>
        = OnEditorActionBinding(predicate, lazy(viewProvider))

private class OnEditorActionBinding(
        val predicate: (Int?, Int?) -> Boolean,
        viewProvider: Lazy<EditText>
) : ReadWriteProperty<Any?, () -> Unit> {

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
        if (function == null) {
            view.setOnEditorActionListener { _, actionId: Int?, event ->
                val handleAction = predicate(actionId, event?.keyCode)
                if (handleAction) function?.invoke()
                return@setOnEditorActionListener handleAction
            }
        }
    }

    companion object {
        val noop: () -> Unit = {}
    }
}