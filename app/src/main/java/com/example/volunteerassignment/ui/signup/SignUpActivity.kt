package com.example.volunteerassignment.ui.signup

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.example.volunteerassignment.R
import com.example.volunteerassignment.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.auth.User
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_signup.*
import kotlinx.coroutines.handleExceptionViaHandler
import java.util.*

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_signup)
        val Goback: ImageButton = findViewById(R.id.img_btn_back)
        Goback.setOnClickListener {
            onBackPressed()
        }
        btnRegister.setOnClickListener {
            createEmailId()
            // linktoLogin()
            clearText()
        }
        btnUploadPhoto.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }
    }

    var selectedPhotoUrl: Uri? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            selectedPhotoUrl = data.data

            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedPhotoUrl)

            uploadphoto_imageview_register.setImageBitmap(bitmap)
            btnUploadPhoto.alpha = 0f
            //val bitmapDrawable = BitmapDrawable(bitmap)
            //btnUploadPhoto.setBackgroundDrawable(bitmapDrawable)
        }
    }

    fun createEmailId() {
        val email = keyinSignupEmail.text.toString()
        val password = editSignupPassword.text.toString()
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter text in email/pw", Toast.LENGTH_SHORT).show()
            return
        }
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (!it.isSuccessful) return@addOnCompleteListener
                Toast.makeText(this, "Register Successfully", Toast.LENGTH_SHORT).show()

                uploadImagetoFirebaseStorage()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to register", Toast.LENGTH_SHORT).show()
            }
    }

    private fun uploadImagetoFirebaseStorage() {
        if (selectedPhotoUrl == null) return

        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")

        ref.putFile(selectedPhotoUrl!!)
            .addOnSuccessListener {
                ref.downloadUrl.addOnSuccessListener {
                    saveUsertoFirebaseDatabase(it.toString())
                }
            }
            .addOnFailureListener {

            }


    }

    private fun saveUsertoFirebaseDatabase(profileImageUrl: String) {
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val ref = FirebaseStorage.getInstance().getReference("/users/$uid")

        // val user = User(uid, keyinSignupUsername.text.toString(), profileImageUrl)

        //ref.setValue(user)
        //.addOnSuccessListener{

    }

//class User(val uid:String,  val username:String, val profileImageUrl:String)

    fun clearText() {
        keyinSignupEmail.text = null
        keyinSignupUsername.text = null
        editSignupPassword.text = null
        //uploadphoto_imageview_register.clearFocus()
    }

}


    //fun linktoLogin() {
       // btnRegister.setOnClickListener {
          //  val intent: Intent = Intent(applicationContext, LoginActivity::class.java)
          //  startActivity(intent)
       // }
   // }



















