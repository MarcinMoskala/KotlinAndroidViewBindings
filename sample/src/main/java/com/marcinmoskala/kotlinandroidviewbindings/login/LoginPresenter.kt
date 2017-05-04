package com.marcinmoskala.kotlinandroidviewbindings.login

import com.marcinmoskala.kotlinandroidviewbindings.applySchedulers
import com.marcinmoskala.kotlinandroidviewbindings.smartSubscribe
import rx.Subscription

class LoginPresenter(val view: LoginView) {

    val loginUseCase by lazy { LoginUseCase() }
    val validateLoginFieldsUseCase by lazy { ValidateLoginFieldsUseCase() }
    var subscriptions: List<Subscription> = emptyList()

    fun onCreate() {
        view.loginButtonClickedCallback = { attemptLogin() }
    }

    fun onDestroy() {
        subscriptions.forEach { it.unsubscribe() }
    }

    fun attemptLogin() {
        val (email, password) = view.email to view.password
        subscriptions += validateLoginFieldsUseCase.validateLogin(email, password)
                .smartSubscribe(
                        onSuccess = { (emailErrorId, passwordErrorId) ->
                            view.passwordErrorId = passwordErrorId
                            view.emailErrorId = emailErrorId
                            when {
                                emailErrorId != null -> view.emailRequestFocus()
                                passwordErrorId != null -> view.passwordRequestFocus()
                                else -> sendLoginRequest(email, password)
                            }
                        },
                        onError = view::informAboutError
                )
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