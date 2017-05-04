# KotlinAndroidViewBindings

Library providing property Kotlin bindings to Android view elements.

[![](https://jitpack.io/v/MarcinMoskala/KotlinAndroidViewBindings.svg)](https://jitpack.io/#MarcinMoskala/KotlinAndroidViewBindings)

Library allows to bind properties of basic types (String, Int, functional) to view element properties. Example:

```kotlin
var email by bindToTextView(R.id.emailView)
val emailRequestFocus by bindToRequestFocus(R.id.emailView)
var emailErrorId by bindToErrorId(R.id.emailView)
```

This allows to make clear and simple MVP. Example:

LoginView.kt
```kotlin
interface LoginView {
    var progressVisible: Boolean
    
    var email: String
    val emailRequestFocus: ()->Unit
    var emailErrorId: Int?
 
    var password: String
    val passwordRequestFocus: ()->Unit
    var passwordErrorId: Int?
    
    var loginButtonClickedCallback: ()->Unit
}
```

LoginActivity.kt
```kotlin
class LoginActivity : AppCompatActivity(), LoginView {
    
    override var progressVisible by bindToLoading(R.id.progressView, R.id.loginFormView)
    
    override var email by bindToTextView(R.id.emailView)
    override val emailRequestFocus by bindToRequestFocus(R.id.emailView)
    override var emailErrorId by bindToErrorId(R.id.emailView)
    
    override var password by bindToTextView(R.id.passwordView)
    override val passwordRequestFocus by bindToRequestFocus(R.id.passwordView)
    override var passwordErrorId by bindToErrorId(R.id.passwordView)
    
    override var loginButtonClickedCallback by bindToClick(R.id.loginButton)
    
    //...
}
```

Presenter.kt
```kotlin
class LoginPresenter(val view: LoginView) {

    fun onCreate() {
        view.loginButtonClickedCallback = { attemptLogin() }
    }
    
    //...
}
```

This makes presenters fully unit-testable while keeping Activities short and simple. Full example [here](https://github.com/MarcinMoskala/KotlinAndroidViewBindings/tree/master/sample/src/main/java/com/marcinmoskala/kotlinandroidviewbindings/login). Library usage in wider context [here](https://github.com/MarcinMoskala/SimpleKotlinMvpBoilerplate).
