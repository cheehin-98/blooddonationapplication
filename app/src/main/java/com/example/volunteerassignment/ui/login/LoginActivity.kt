package com.example.volunteerassignment.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.example.volunteerassignment.R
import com.example.volunteerassignment.ui.signup.SignUpActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_signup.*

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_login)
        auth = FirebaseAuth.getInstance()// get connection with database


        val login: Button = findViewById(R.id.btnLogin)
        login.setOnClickListener {
        }

        val lintoSignup: TextView = findViewById(R.id.linktoSignup)
        linktoSignup.setOnClickListener {
            val intent: Intent = Intent(applicationContext, SignUpActivity::class.java)
            startActivity(intent)
        }

        val goback: ImageButton = findViewById(R.id.img_btn_clear)
        goback.setOnClickListener {
            onBackPressed()
        }
        btnLogin.setOnClickListener {
            Login()
        }
    }

    private fun Login() {
        val email = keyinUsername.text.toString()
        val password = keyinPassword.text.toString()
        //if (email.isEmpty() || password.isEmpty()) {
        //    Toast.makeText(this, "Please enter text in email/pw", Toast.LENGTH_SHORT).show()
        //   return
        // }
        //  FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
        // .addOnCompleteListener {
        //    if (!it.isSuccessful) return@addOnCompleteListener
        //    Toast.makeText(this, "Login Successfully", Toast.LENGTH_SHORT).show()

        //}
        // .addOnFailureListener {
        //     Toast.makeText(this, "Failed to register", Toast.LENGTH_SHORT).show()
        //  }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (user != null) {
                        updateUI(user)
                        Toast.makeText(
                            baseContext, "Login Successfully.",
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                } else {
                    Toast.makeText(
                        baseContext, "Login failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }
            }
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            updateUI(currentUser)
        }
    }


    private fun updateUI(currentUser: FirebaseUser?) {

    }
}






