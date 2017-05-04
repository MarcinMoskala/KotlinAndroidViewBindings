package com.marcinmoskala.kotlinandroidviewbindings

import android.content.Context
import android.support.annotation.IdRes
import android.support.v7.app.AppCompatActivity
import android.widget.EditText
import android.widget.Toast
import rx.Observable
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

inline fun <reified T> AppCompatActivity.bindView(viewId: Int) = lazy { findViewById(viewId) as T }

fun <T> Observable<T>.applySchedulers(): Observable<T> =
        subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

fun <T> Observable<T>.smartSubscribe(
        onStart: (() -> Unit)? = null,
        onError: ((Throwable) -> Unit)? = null,
        onFinish: (() -> Unit)? = null,
        onSuccess: (T) -> Unit = {}): Subscription =
        addStartFinishActions(onStart, onFinish)
                .subscribe(onSuccess, { onError?.invoke(it) })

fun <T> Observable<T>.addStartFinishActions(onStart: (() -> Unit)? = null, onFinish: (() -> Unit)? = null): Observable<T> {
    onStart?.invoke()
    return doOnTerminate({ onFinish?.invoke() })
}

fun Context.toast(text: String, length: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, text, length).show()
}

fun EditText.setErrorId(@IdRes errorId: Int?) {
    this.error = if (errorId == null) null else context.getString(errorId)
}