package com.example.volunteerassignment.ui.signup

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.volunteerassignment.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.fragment_signup.*

class SignUpActivity : AppCompatActivity() {

    private lateinit var userSpinner: Spinner
    private lateinit var btnRegister: Button
    private lateinit var circlrImageView : CircleImageView
    private lateinit var dataUri:Uri

    private lateinit var storage: FirebaseStorage
    private lateinit var ref: FirebaseFirestore
    private lateinit var mAuth: FirebaseAuth

    val REQUEST_CODE_PROFILE = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_signup)

        ref = FirebaseFirestore.getInstance()
        storage = FirebaseStorage.getInstance()
        mAuth = FirebaseAuth.getInstance()

        val goback: ImageButton = findViewById(R.id.img_btn_back)
        userSpinner = findViewById(R.id.spinnerUser)
        btnRegister = findViewById(R.id.btnRegister)
        circlrImageView = findViewById(R.id.uploadphoto_imageview_register)

        dataUri = Uri.EMPTY

        goback.setOnClickListener {
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
            startActivityForResult(intent, REQUEST_CODE_PROFILE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(data != null && data.getData() != null && resultCode == Activity.RESULT_OK){

            dataUri = data.data as Uri

            if(requestCode == REQUEST_CODE_PROFILE){
                circlrImageView.setImageURI(dataUri)
                btnUploadPhoto.alpha = 0f
            }
        }else{
            Toast.makeText(this, "Image Failed", Toast.LENGTH_SHORT).show()
        }
    }

    fun createEmailId() {
        val name = keyinSignupUsername.text.toString()
        val email = keyinSignupEmail.text.toString()
        val password = editSignupPassword.text.toString()
        var userType = userSpinner.selectedItem.toString()


        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter text in email/pw", Toast.LENGTH_SHORT).show()
            return
        }
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val UID = mAuth.currentUser!!.uid

                    //if(userType == "Organizer")
                    val user = hashMapOf(
                        "Name" to name,
                        "Point" to 0,
                        "Email" to email,
                        "Type" to userType
                    )
                    ref.collection("Users").document(UID)
                        .set(user).addOnSuccessListener {

                            if(dataUri != Uri.EMPTY) {
                                val storageRef = storage.reference.child("User/" + UID)

                                val profileRef = storageRef.child("profile.jpg")

                                profileRef.putFile(dataUri).addOnSuccessListener {
                                    Toast.makeText(this, "Registered", Toast.LENGTH_SHORT).show()
                                }.addOnFailureListener {
                                    Toast.makeText(this, "Failed Register1", Toast.LENGTH_SHORT)
                                        .show()
                                }
                            }
                        }
                } else {
                    Toast.makeText(this, "Failed registered2", Toast.LENGTH_SHORT).show()
                }
            }
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



















