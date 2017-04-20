package com.marcinmoskala.kotlinandroidviewbindings

import com.mvtest.marcinmoskala.mvtest.*
import rx.Subscription

class LoginPresenter(val view: LoginView) {

    val loginUseCase by lazy { LoginUseCase() }
    val validateLoginFieldsUseCase by lazy { ValidateLoginFieldsUseCase() }
    var subscriptions: List<Subscription> = emptyList()

    fun onDestroy() {
        subscriptions.forEach { it.unsubscribe() }
    }

    fun attemptLogin() {
        val (email, password) = view.email to view.password
        subscriptions += validateLoginFieldsUseCase.validateLogin(email, password)
                .smartSubscribe { (emailErrorId, passwordErrorId) ->
                    view.passwordErrorId = passwordErrorId
                    view.emailErrorId = emailErrorId
                    when {
                        emailErrorId != null -> view.requestEmailFocus()
                        passwordErrorId != null -> view.requestPasswordFocus()
                        else -> sendLoginRequest(email, password)
                    }
                }
    }

    private fun sendLoginRequest(email: String, password: String) {
        loginUseCase.sendLoginRequest(email, password)
                .applySchedulers()
                .smartSubscribe(
                        onStart = { view.progressVisible = true },
                        onSuccess = { (token) -> view.informAboutLoginSuccess(token) },
                        onError = view::informAboutError,
                        onFinish = { view.progressVisible = false }
                )
    }
}