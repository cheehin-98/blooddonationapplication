package com.example.volunteerassignment.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.*
import com.example.volunteerassignment.R
import com.example.volunteerassignment.ui.signup.SignUpActivity
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_login.*

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var ref: FirebaseFirestore

    private lateinit var emailEditTxt:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_login)
        auth = FirebaseAuth.getInstance()
        ref = FirebaseFirestore.getInstance()

        val extras = this.intent.extras
        val email = extras?.getString("Email")

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

        val goback: ImageButton = findViewById(R.id.img_btn_clear)
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

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (user != null) {
                        //updateUI(user)
                        Toast.makeText(this, "Login Successfully.", Toast.LENGTH_SHORT).show()
                    this.finish()
                    }
                } else {
                    Toast.makeText(this, "Login failed.", Toast.LENGTH_SHORT).show()
                    //updateUI(null)
                }
            }
    }



    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            val navView: NavigationView = findViewById(R.id.nav_view)
            val drawer: Menu = navView.menu

//            ref.collection("Users").document(currentUser.uid)
//                .get()
//                .addOnSuccessListener { documentSnapshot ->
//                    if(documentSnapshot.exists()){
//                        if(documentSnapshot.get("Type").toString() =="Volunteer"){
//                            val userNavigate = drawer.findItem(R.id.user_navigate)
//                            userNavigate.setVisible(true)
//
//                        }else if(documentSnapshot.get("Type").toString() =="Organizer"){
//                            val organizerNavigate = drawer.findItem(R.id.organization_navigate)
//                            organizerNavigate.setVisible(true)
//                        }
//                    }
//                }
//                .addOnFailureListener { exception ->
//                    Toast.makeText(this, "Unable to update UI!", Toast.LENGTH_SHORT).show()
//                }
        } else {
            return
        }
    }
}






