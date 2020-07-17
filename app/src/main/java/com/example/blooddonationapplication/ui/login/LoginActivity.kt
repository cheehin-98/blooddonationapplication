package com.example.blooddonationapplication.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.*
import com.example.blooddonationapplication.MainActivity
import com.example.blooddonationapplication.R
import com.example.blooddonationapplication.ui.signup.SignUpActivity
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_login_page.*
import kotlinx.android.synthetic.main.fragment_login_page.keyinPassword
import kotlinx.android.synthetic.main.fragment_login_page.keyinUsername
import kotlinx.android.synthetic.main.fragment_login_page.linktoSignup
import kotlinx.android.synthetic.main.fragment_login_page.*

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var ref: FirebaseFirestore

    private lateinit var emailEditTxt:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_login_page)
        auth = FirebaseAuth.getInstance()
        ref = FirebaseFirestore.getInstance()

        val extras = this.intent.extras
        val email = extras?.getString("Email")

        val progressBar = progressBarLogin

        if(email !=null){
            emailEditTxt=findViewById(R.id.keyinUsername)
            emailEditTxt.setText(email.toString())
        }

        val login: Button = findViewById(R.id.btnLogin)
        login.setOnClickListener {
            Login()
        }

        val lintoSignup: TextView = findViewById(R.id.linktoSignup)
        linktoSignup.setOnClickListener {
            val intent: Intent = Intent(applicationContext, SignUpActivity::class.java)
            startActivity(intent)
            this.finish()
        }

        val goback: ImageView= findViewById(R.id.img_btn_clear)
        goback.setOnClickListener {
            this.finish()
        }
    }

    private fun Login() {
        val email = keyinUsername.text.toString()
        val password = keyinPassword.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter text in email/pw", Toast.LENGTH_SHORT).show()
            return
        }

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (!it.isSuccessful) return@addOnCompleteListener
            Log.d("Main", "Successfully login with uid: ${it.result?.user?.uid}")
        }
            .addOnFailureListener {
                Log.d("Main", "Failed to create user: ${it.message}")
                Toast.makeText(this, "Failed to login: ${it.message}", Toast.LENGTH_SHORT)
                    .show()
            }
        progressBarLogin.setVisibility(View.VISIBLE)
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (user != null) {
                        //updateUI(user)
                        val intent = Intent(applicationContext, MainActivity::class.java)
                        intent.putExtra("email",email)
                        this.startActivity(intent)

                        Toast.makeText(this, "Login Successfully.", Toast.LENGTH_SHORT).show()
                    this.finish()
                    }
                } else {
                    Toast.makeText(this, "Login failed.", Toast.LENGTH_SHORT).show()
                    progressBarLogin.setVisibility(View.GONE)
                    //updateUI(null)
                }
            }
    }



    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            val navView: NavigationView = findViewById(R.id.nav_view)
            val drawer: Menu = navView.menu

//
        } else {
            return
        }
    }
}






