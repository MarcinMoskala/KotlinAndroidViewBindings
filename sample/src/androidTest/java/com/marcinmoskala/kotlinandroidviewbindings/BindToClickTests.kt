package com.marcinmoskala.kotlinandroidviewbindings

import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import android.widget.Button
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BindToClickTests {

    val context: Context
        get() = InstrumentationRegistry.getTargetContext()

    @Test
    fun buttonBindToClick() {
        val button = Button(context)
        var buttonText by button.bindToText()
        val text1 = "A"
        val text2 = "B"
        buttonText = text1
        assertEquals(text1, buttonText)
        assertEquals(text1, button.text.toString())
        buttonText = text2
        assertEquals(text2, buttonText)
        assertEquals(text2, button.text.toString())
    }
}