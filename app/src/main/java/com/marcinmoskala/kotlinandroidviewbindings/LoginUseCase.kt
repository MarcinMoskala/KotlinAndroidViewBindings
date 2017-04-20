package com.marcinmoskala.kotlinandroidviewbindings

import com.marcinmoskala.kotlinandroidviewbindings.LoginRepository

class LoginUseCase {

    val loginRepository by LoginRepository.lazyGet()

    fun sendLoginRequest(email: String, password: String) = loginRepository
            .attemptLogin(email, password)
}