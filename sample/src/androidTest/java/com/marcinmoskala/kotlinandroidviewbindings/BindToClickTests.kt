package com.marcinmoskala.kotlinandroidviewbindings

import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import android.widget.Button
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BindToClickTests {

    val context: Context
        get() = InstrumentationRegistry.getTargetContext()

    @Test
    fun buttonBindToClickClickCalled() {
        val button = Button(context)
        var buttonClicked by button.bindToClick()
        var invoked = false
        buttonClicked = { invoked = true }
        button.callOnClick()
        assert(invoked)
    }
    @Test
    fun buttonBindToClickClickPerformed() {
        val button = Button(context)
        var buttonClicked by button.bindToClick()
        var invoked = false
        buttonClicked = { invoked = true }
        button.performClick()
        assert(invoked)
    }
}