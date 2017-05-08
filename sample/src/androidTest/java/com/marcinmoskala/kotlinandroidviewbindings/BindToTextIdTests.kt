package com.marcinmoskala.kotlinandroidviewbindings

import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BindToTextIdTests {

    val context: Context get() = InstrumentationRegistry.getTargetContext()

    val text1id get() = android.R.string.copy
    val text1 get() = context.getText(android.R.string.copy)
    val text2id get() = android.R.string.cancel
    val text2 get() = context.getText(android.R.string.cancel)

    @Test
    fun buttonBindIdToText() {
        val button = Button(context)
        var buttonTextId by button.bindToTextId()
        buttonTextId = text1id
        assertEquals(text1id, buttonTextId)
        assertEquals(text1, button.text.toString())
        buttonTextId = text2id
        assertEquals(text2id, buttonTextId)
        assertEquals(text2, button.text.toString())
    }

    @Test
    fun textViewBindIdToText() {
        val textView = TextView(context)
        var textViewTextId by textView.bindToTextId()
        textViewTextId = text1id
        assertEquals(text1id, textViewTextId)
        assertEquals(text1, textView.text.toString())
        textViewTextId = text2id
        assertEquals(text2id, textViewTextId)
        assertEquals(text2, textView.text.toString())
    }

    @Test
    fun editTextBindIdToText() {
        val editText = EditText(context)
        var editTextTextId by editText.bindToTextId()
        editTextTextId = text1id
        assertEquals(text1id, editTextTextId)
        assertEquals(text1, editText.text.toString())
        editTextTextId = text2id
        assertEquals(text2id, editTextTextId)
        assertEquals(text2, editText.text.toString())
    }
}