package com.marcinmoskala.kotlinandroidviewbindings

import com.marcinmoskala.kotlinandroidviewbindings.LoginResponse
import rx.Observable

interface LoginRepository {

    fun attemptLogin(email: String, pass: String): Observable<LoginResponse>

    class MockLoginRepository : LoginRepository {
        override fun attemptLogin(email: String, pass: String): Observable<LoginResponse> = when {
            email.endsWith(".pl") -> throw Error("Invalid Email")
            else -> Observable.just(LoginResponse("TokenToken"))
        }
    }

    companion object {
        fun lazyGet(): Lazy<LoginRepository> = lazy { MockLoginRepository() }
    }
}