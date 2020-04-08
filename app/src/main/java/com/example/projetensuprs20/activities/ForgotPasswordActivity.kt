package com.example.projetensuprs20.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.projetensuprs20.*
import com.example.projetf2.LoginActivity
import com.example.projetf2.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_forgot_password.*

class ForgotPasswordActivity : AppCompatActivity() {

    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance()}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        editTextEmail.validate{
            editTextEmail.error = if(isValidEmail(it)) null else "Email non valide"
        }

            buttonForgot.setOnClickListener {

                val email = editTextEmail.text.toString()
                if(isValidEmail(email)) {
                    mAuth.sendPasswordResetEmail(email).addOnCompleteListener(this){
                        toast("Envoi du un mail de réinitialisation de votre mot de passe")
                        val intent = Intent(this, LoginActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    }
                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)

                } else {
                    toast("Compte non existant avec ce mail")
                }
            }

        buttonGoLogIn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)

            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        }
    }
}
