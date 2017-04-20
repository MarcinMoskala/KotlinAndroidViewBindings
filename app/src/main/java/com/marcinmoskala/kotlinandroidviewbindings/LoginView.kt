package com.marcinmoskala.kotlinandroidviewbindings

    interface LoginView {
        var progressVisible: Boolean
        var email: String
        var password: String
        var emailErrorId: Int?
        var passwordErrorId: Int?
        fun requestEmailFocus()
        fun requestPasswordFocus()
        fun informAboutLoginSuccess(token: String)
        fun informAboutError(error: Throwable)
    }