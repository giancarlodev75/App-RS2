package com.example.projetensuprs20.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import android.widget.Toast
import android.content.Intent
import com.example.projetensuprs20.*
import com.example.projetf2.LoginActivity
import com.example.projetf2.R
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    // Lazy
    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance()}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        buttonForgot.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        buttonSignUp.setOnClickListener {
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()
            val confirmPassword = editTextConfirmPassword.text.toString()

            if(isValidEmail(email) && isValidPassword(password) && isValidConfirmPassword(password, confirmPassword)){
                signupByEmail(email, password)
            } else {
                toast("Vérifiez les données saisies")
                // Toast.makeText(this, "Vérifier votre saisie", Toast.LENGTH_SHORT).show()
            }
        }

        editTextEmail.validate{
            editTextEmail.error = if(isValidEmail(it)) null else "Email non valide"
        }
        editTextPassword.validate{
            editTextPassword.error = if(isValidPassword(it)) null else "Le mot de passe doit contenir au moins 8 caractères."
        }

        editTextConfirmPassword.validate{
            editTextConfirmPassword.error = if (isValidConfirmPassword(editTextPassword.text.toString(), it))
                null else "Confirmer le mot de passe"
        }

    }

    private fun signupByEmail(email: String, password: String){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {

                mAuth.currentUser!!.sendEmailVerification().addOnCompleteListener(this){
                    toast("Votre compte a été crée. Un email vous a été envoyé")
                }
                // Toast.makeText(this, "Votre compte a été crée. Un email vous a été envoyé", Toast.LENGTH_SHORT).show()
                //val user = mAuth.currentUser
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)

                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)

            }
            else {
                // toast("Erreur dans la création de votre compte")
                Toast.makeText(this, "Erreur dans la création de votre compte", Toast.LENGTH_SHORT).show()
            }
        }
    }

}










