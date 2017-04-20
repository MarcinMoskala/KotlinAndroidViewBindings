package com.marcinmoskala.kotlinandroidviewbindings_kae

import android.view.View
import android.widget.EditText
import android.widget.TextView
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

@JvmName("bindStringToTextViewText")
fun bindToText(viewProvider: () -> TextView): ReadWriteProperty<Any?, String>
        = TextViewTextBinding(viewProvider)

@JvmName("bindStringToEditTextText")
fun bindToText(viewProvider: () -> EditText): ReadWriteProperty<Any?, String>
        = EditTextViewTextBinding(viewProvider)

@JvmName("bindStringToEditTextError")
fun bindToErrorId(viewProvider: () -> EditText): ReadWriteProperty<Any?, Int?>
        = EditTextViewErrorIdBinding(viewProvider)

fun bindViewOnClick(viewProvider: () -> View): ReadWriteProperty<Any?, ()->Unit>
        = OnClickBinding(viewProvider)

fun bindViewOnLongClick(viewProvider: () -> EditText): ReadWriteProperty<Any?, ()->Unit>
        = OnLongClickBinding(viewProvider)

private class OnClickBinding(viewProvider: () -> View) : ReadWriteProperty<Any?, ()->Unit> {

    val view by lazy(viewProvider)
    var function = {}

    override fun getValue(thisRef: Any?, property: KProperty<*>): ()->Unit {
        return function
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: ()->Unit) {
        function = value
        view.setOnClickListener { function() }
    }
}

private class OnLongClickBinding(viewProvider: () -> View) : ReadWriteProperty<Any?, ()->Unit> {

    val view by lazy(viewProvider)
    var function = {}

    override fun getValue(thisRef: Any?, property: KProperty<*>): ()->Unit {
        return function
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: ()->Unit) {
        function = value
        view.setOnLongClickListener { function(); true }
    }
}

private class TextViewTextBinding(viewProvider: () -> TextView) : ReadWriteProperty<Any?, String> {

    val view by lazy(viewProvider)

    override fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return view.text.toString()
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        view.text = value
    }
}

private class EditTextViewTextBinding(viewProvider: () -> EditText) : ReadWriteProperty<Any?, String> {

    val view by lazy(viewProvider)

    override fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return view.text.toString()
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        view.setText(value)
    }
}

private class EditTextViewErrorIdBinding(viewProvider: () -> EditText) : ReadWriteProperty<Any?, Int?> {

    val view by lazy(viewProvider)
    var currentError: Int? = null

    override fun getValue(thisRef: Any?, property: KProperty<*>): Int? {
        return currentError
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: Int?) {
        currentError = value
        view.error = value?.let { view.context.getString(value) }
    }
}
