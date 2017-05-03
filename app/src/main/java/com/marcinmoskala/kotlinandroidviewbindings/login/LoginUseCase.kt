package com.marcinmoskala.kotlinandroidviewbindings.login

class LoginUseCase {

    val loginRepository by LoginRepository.lazyGet()

    fun sendLoginRequest(email: String, password: String) = loginRepository
            .attemptLogin(email, password)
}