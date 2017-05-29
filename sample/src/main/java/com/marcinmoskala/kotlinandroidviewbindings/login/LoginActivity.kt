package com.marcinmoskala.kotlinandroidviewbindings.login

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import com.marcinmoskala.kotlinandroidviewbindings.*

class LoginActivity : AppCompatActivity(), LoginView {

    override var progressVisible by bindToLoading(R.id.progressView, R.id.loginFormView)

    override var email by bindToText(R.id.emailView)
    override val emailRequestFocus by bindToRequestFocus(R.id.emailView)
    override var emailErrorId by bindToErrorId(R.id.emailView)
    override var onEmaiEnterPressed by bindToEditorActions(R.id.emailView) { actionId, eventCode ->
        actionId == EditorInfo.IME_ACTION_DONE || eventCode == KeyEvent.KEYCODE_ENTER
    }

    override var password by bindToText(R.id.passwordView)
    override val passwordRequestFocus by bindToRequestFocus(R.id.passwordView)
    override var passwordErrorId by bindToErrorId(R.id.passwordView)
    override var onPasswordEnterPressed by bindToEditorActions(R.id.passwordView) { actionId, eventCode ->
        actionId == EditorInfo.IME_ACTION_DONE || eventCode == KeyEvent.KEYCODE_ENTER
    }

    override var loginButtonClickedCallback by bindToClick(R.id.loginButton)

    val presenter by lazy { LoginPresenter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        presenter.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun informAboutLoginSuccess(token: String) {
        toast("Login succeed. Token: $token")
    }

    override fun informAboutError(error: Throwable) {
        toast("Error: " + error.message)
    }
}