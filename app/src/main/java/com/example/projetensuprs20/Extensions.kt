package com.example.projetensuprs20

import android.app.Activity
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.util.regex.Pattern



// LayoutInflater
fun ViewGroup.inflate(layoutId : Int) =
    LayoutInflater.from(context).inflate(layoutId, this, false)!!


// Intent (activity)
inline fun <reified  T: Activity> Activity.goToActivity(noinline init: Intent.()
-> Unit = {}){
    val intent = Intent(this, T::class.java)
    intent.init()
    startActivity(intent)
}

// toast avec chaine de caracteres
fun Activity.toast(message: CharSequence, duration:Int = Toast.LENGTH_SHORT) = Toast.makeText(this, message, duration).show()

// Vérification des champs de création de compte pendant la saisie
fun EditText.validate(validation: (String) -> Unit ) {
    this.addTextChangedListener(object: TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            validation(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

    })
}

fun Activity.isValidEmail(email: String): Boolean {
    val pattern = Patterns.EMAIL_ADDRESS
    return pattern.matcher(email).matches()
}

fun Activity.isValidPassword(password: String): Boolean {
    val passwordPattern = "^[\\w]{8,}$"
    val pattern = Pattern.compile(passwordPattern)
    return pattern.matcher(password).matches()
}

fun Activity.isValidConfirmPassword(password: String, confirmPassword: String): Boolean {
    return password == confirmPassword
}
