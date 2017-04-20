package com.marcinmoskala.kotlinandroidviewbindings

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.EditorInfo
import com.marcinmoskala.kotlinandroidviewbindings.bindToErrorId
import com.marcinmoskala.kotlinandroidviewbindings.bindToText
import com.marcinmoskala.kotlinandroidviewbindings.LoginPresenter
import com.marcinmoskala.kotlinandroidviewbindings.LoginView
import com.marcinmoskala.kotlinandroidviewbindings.toast
import kotlinx.android.synthetic.main.activity_login.*
import kotlin.properties.Delegates

class LoginActivity : AppCompatActivity(), LoginView {

    override var progressVisible by Delegates.observable(false) { _, _, n ->
        progressView.visibility = if (n) View.VISIBLE else View.GONE
        loginFormView.visibility = if (n) View.GONE else View.VISIBLE
    }
    override var email: String by bindToText { emailView }
    override var password: String by bindToText { passwordView }
    override var emailErrorId: Int? by bindToErrorId { emailView }
    override var passwordErrorId: Int? by bindToErrorId { passwordView }

    val presenter by lazy { LoginPresenter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        passwordView.setOnEditorActionListener { _, id, _ ->
            when (id) {
                EditorInfo.IME_NULL -> {
                    presenter.attemptLogin()
                    true
                }
                else -> false
            }
        }
        loginButton.setOnClickListener { presenter.attemptLogin() }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun requestEmailFocus() {
        emailView.requestFocus()
    }

    override fun requestPasswordFocus() {
        passwordView.requestFocus()
    }

    override fun informAboutLoginSuccess(token: String) {
        toast("Login succeed. Token: $token")
    }

    override fun informAboutError(error: Throwable) {
        toast("Error: " + error.message)
    }
}

