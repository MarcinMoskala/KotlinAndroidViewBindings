package com.marcinmoskala.kotlinandroidviewbindings

import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import org.junit.Test
import org.junit.runner.RunWith
import android.view.inputmethod.BaseInputConnection



@RunWith(AndroidJUnit4::class)
class BindToActionTests {

    val context: Context
        get() = InstrumentationRegistry.getTargetContext()

    @Test
    fun bindToEditorActionsInvokedWhenActionCalled() {
        val editText = EditText(context)
        var buttonClicked by editText.bindToEditorActions { actionId, eventCode -> actionId == EditorInfo.IME_ACTION_DONE || eventCode == KeyEvent.KEYCODE_ENTER }
        var invoked = false
        buttonClicked = { invoked = true }
        editText.onEditorAction(EditorInfo.IME_ACTION_DONE)
        assert(invoked)
    }

    @Test
    fun bindToEditorActionsInvokedWhenKeyClickedCalled() {
        val editText = EditText(context)
        var buttonClicked by editText.bindToEditorActions { actionId, eventCode -> actionId == EditorInfo.IME_ACTION_DONE || eventCode == KeyEvent.KEYCODE_ENTER }
        var invoked = false
        buttonClicked = { invoked = true }
        BaseInputConnection(editText, true).sendKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER))
        assert(invoked)
    }
}