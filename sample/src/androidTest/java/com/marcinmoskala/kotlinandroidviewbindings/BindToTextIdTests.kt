package com.marcinmoskala.kotlinandroidviewbindings

import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import android.widget.Button
import android.widget.TextView
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BindToTextIdTests {

    val context: Context
        get() = InstrumentationRegistry.getTargetContext()

    @Test
    fun buttonBindIdToText() {
        val button = Button(context)
        var buttonTextId by button.bindToTextId()
        val text1id = android.R.string.copy
        val text1 = context.getText(android.R.string.copy)
        val text2id = android.R.string.cancel
        val text2 = context.getText(android.R.string.cancel)
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
        var buttonTextId by textView.bindToTextId()
        val text1id = android.R.string.copy
        val text1 = context.getText(android.R.string.copy)
        val text2id = android.R.string.cancel
        val text2 = context.getText(android.R.string.cancel)
        buttonTextId = text1id
        assertEquals(text1id, buttonTextId)
        assertEquals(text1, textView.text.toString())
        buttonTextId = text2id
        assertEquals(text2id, buttonTextId)
        assertEquals(text2, textView.text.toString())
    }
}