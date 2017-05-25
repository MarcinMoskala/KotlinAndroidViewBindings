package com.marcinmoskala.kotlinandroidviewbindings.login

interface LoginView {
    var progressVisible: Boolean
    var email: String
    val emailRequestFocus: ()->Unit
    var emailErrorId: Int?
    var onEmaiEnterPressed: ()->Unit
    var password: String
    val passwordRequestFocus: ()->Unit
    var passwordErrorId: Int?
    var onPasswordEnterPressed: ()->Unit
    var loginButtonClickedCallback: ()->Unit
    fun informAboutLoginSuccess(token: String)
    fun informAboutError(error: Throwable)
}