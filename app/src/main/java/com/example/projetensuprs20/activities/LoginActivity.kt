package com.example.projetf2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.projetensuprs20.*
import com.example.projetensuprs20.activities.*
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener {


    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance()}
    private val mGoogleApiClient: GoogleApiClient by lazy { getGoogleApiClient()}
    private val RC_GOOGLE_SIGN_IN = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        buttonSignIn.setOnClickListener{
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()
            if(isValidEmail(email) && isValidPassword(password)){
                loginInByEmail(email, password)
                toast("Vous etes connecté")
            }else{
                toast("Vérifier vos données de connexion")
            }
        }

        textViewForgotPassword.setOnClickListener{
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            //intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        }

        buttonSignUp.setOnClickListener{
            val intent = Intent(this, SignUpActivity::class.java)
            //intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        }

        login_sign_in_google.setOnClickListener{
            val signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
            startActivityForResult(signInIntent, RC_GOOGLE_SIGN_IN)
        }

        editTextEmail.validate{
            editTextEmail.error = if(isValidEmail(it)) null else "Email non valide"
        }

        editTextPassword.validate{
            editTextPassword.error = if(isValidPassword(it)) null else "Le mot de passe doit contenir au moins 8 caractères."
        }
    }
    // Partie 1
    private fun getGoogleApiClient(): GoogleApiClient{
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        return GoogleApiClient.Builder(this)
            .enableAutoManage(this, this)
            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
            .build()
    }

    private fun loginInByEmail(email: String, password: String){
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) {task ->
            if (task.isSuccessful) {
                if(mAuth.currentUser!!.isEmailVerified){
                    toast("Vous etes connecté")
                } else {
                    toast("Validez le mail qui vous a été envoyé")
                }
            } else {
                toast("Erreur de connection")
            }
        }
    }

    // Partie 4
    private fun loginGoogleavecFirebase(googleAccount: GoogleSignInAccount){
        val credential = GoogleAuthProvider.getCredential(googleAccount.idToken, null)
        mAuth.signInWithCredential(credential).addOnCompleteListener(this){
            toast("Vous êtes connecté avec votre compte Google")
        }
    }

    // Partie 1B
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == RC_GOOGLE_SIGN_IN){
            val resultat = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if(resultat?.isSuccess!!){
                val account = resultat.signInAccount
                loginGoogleavecFirebase(account!!)
            }
        }
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        toast("La connexion a échoué")
    }
    /*private fun isValidEmailAndPassword(email:String, password: String):Boolean{
        return !email.isNullOrEmpty() && !password.isNullOrEmpty()
    }*/

}
