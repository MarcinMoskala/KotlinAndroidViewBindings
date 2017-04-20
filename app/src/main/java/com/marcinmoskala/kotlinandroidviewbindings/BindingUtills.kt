package com.marcinmoskala.kotlinandroidviewbindings

import android.widget.EditText
import android.widget.TextView
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

@JvmName("bindStringToTextViewText") fun bindToText(viewProvider: () -> TextView): ReadWriteProperty<Any?, String> = TextViewTextBinding(viewProvider)

@JvmName("bindStringToEditTextText") fun bindToText(viewProvider: () -> EditText): ReadWriteProperty<Any?, String> = EditTextViewTextBinding(viewProvider)

@JvmName("bindStringToEditTextError") fun bindToErrorId(viewProvider: () -> EditText): ReadWriteProperty<Any?, Int?> = EditTextViewErrorIdBinding(viewProvider)

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
