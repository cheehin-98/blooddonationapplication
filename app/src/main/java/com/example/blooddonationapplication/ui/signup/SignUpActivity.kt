package com.example.blooddonationapplication.ui.signup

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import com.example.blooddonationapplication.R
import com.example.blooddonationapplication.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.storage.FirebaseStorage
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_register.btnUploadPhoto
import kotlinx.android.synthetic.main.fragment_register.editSignupPassword
import kotlinx.android.synthetic.main.fragment_register.keyinSignupEmail
import kotlinx.android.synthetic.main.fragment_register.keyinSignupUsername
import kotlinx.android.synthetic.main.fragment_user_my_account.*
import java.util.*
import kotlin.collections.HashMap

class SignUpActivity : AppCompatActivity() {

    private lateinit var userSpinner: Spinner
    private lateinit var userBloodType: Spinner
    private lateinit var userGender: Spinner
    private lateinit var btnRegister: Button
    private lateinit var circlrImageView: CircleImageView
    private lateinit var dataUri: Uri

    private lateinit var storage: FirebaseStorage
    private lateinit var ref: FirebaseFirestore
    private lateinit var mAuth: FirebaseAuth
    private lateinit var RootRef: DatabaseReference;

    //private lateinit var auth :FirebaseDatabase
    // private lateinit var database: DatabaseReference
    val REQUEST_CODE_PROFILE = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_register)

        ref = FirebaseFirestore.getInstance()
        storage = FirebaseStorage.getInstance()
        mAuth = FirebaseAuth.getInstance()
        RootRef = FirebaseDatabase.getInstance().getReference();

        val goback: ImageView = findViewById(R.id.imgBacktoPrevious)
        userSpinner = findViewById(R.id.spinnerUser)
        btnRegister = findViewById(R.id.btnRegister)
        circlrImageView = findViewById(R.id.uploadphoto_imageview_register)
        val progressBar = progressBarRegister

        dataUri = Uri.EMPTY

        goback.setOnClickListener {
            this.finish()
        }
        btnRegister.setOnClickListener {

            createEmailId()
        }
        btnUploadPhoto.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_CODE_PROFILE)
        }
    }

    //var selectedPhotoUri: Uri? = null
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        /*selectedPhotoUri = data!!.data as Uri
        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {

            circlrImageView.setImageURI(selectedPhotoUri)
            btnUploadPhoto.alpha = 0f
            Log.d("SignUpActivity", "Photo was selected")
        }
        val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedPhotoUri)

        val bitmapDrawable = BitmapDrawable(bitmap)
        btnUploadPhoto.setBackgroundDrawable(bitmapDrawable)*/


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
        val userType = userSpinner.selectedItem.toString()
      // val bloodType = userBloodType.onItemSelectedListener.toString()
       //val gender = userGender.onItemSelectedListener.toString()


        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter text in email/pw", Toast.LENGTH_SHORT).show()
            return
        }
      /* FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (!it.isSuccessful) return@addOnCompleteListener
            Log.d("Main", "Successfully Created User with uid: ${it.result?.user?.uid}")
            //FirebaseDatabase.getInstance().getReference().child("/Users")
            //database.child("Users").child("username").setValue(name)

        }
            .addOnFailureListener {
                Log.d("Main", "Failed to create user: ${it.message}")
                Toast.makeText(this, "Failed to create user: ${it.message}", Toast.LENGTH_SHORT)
                    .show()
            }*/
        progressBarRegister.setVisibility(View.VISIBLE)

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val UID = mAuth.currentUser!!.uid
                    var user: HashMap<String, Any>
                    if (userType == "Organizer") {
                        user = hashMapOf(
                            "Name" to name,
                            "Email" to email,
                            "Type" to userType
                           // "Blood Type" to  bloodType,
                           // "Gender" to  gender
                        )
                    } else {
                        user = hashMapOf(
                            "Name" to name,
                            "Email" to email,
                            "Type" to userType
                            //  "Blood Type" to  bloodType,
                            //"Gender" to  gender
                        )

                    }

                    ref.collection("Users").document(UID)
                        .set(user).addOnSuccessListener {

                            if(dataUri != Uri.EMPTY) {
                                val storageRef = storage.reference.child("User/" + UID)

                                val profileRef = storageRef.child("profile.jpg")

                                profileRef.putFile(dataUri).addOnSuccessListener {
                                   // Toast.makeText(this, "Registered", Toast.LENGTH_SHORT).show()
                                    //uploadImageToFirebaseStorage()

                                    mAuth.createUserWithEmailAndPassword(
                                        email,
                                        password
                                    ).addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            //Get device token
                                            FirebaseInstanceId.getInstance()
                                                .instanceId.addOnCompleteListener { task ->
                                                if (task.isSuccessful) {

                                                    val currentUserID = mAuth.currentUser!!.uid

                                                    val profileMap = mutableMapOf<String,String>()
                                                    profileMap.put("uid", currentUserID)
                                                    profileMap.put("email", email)
                                                    profileMap.put("userType", userType)

                                                    RootRef.child("Users").child(currentUserID)
                                                        .setValue(profileMap)


                                                    Toast.makeText(
                                                        applicationContext,
                                                        "Account Successfully Create",
                                                        Toast.LENGTH_LONG
                                                    ).show()
                                                   /* startActivity(
                                                        Intent(
                                                            applicationContext,
                                                            LoginActivity::class.java
                                                        )
                                                    )*/
                                                    //finish()

                                                }
                                            }
                                        } else {

                                            val FireBaseRegisterMessage =
                                                task.exception!!.toString()
                                            Toast.makeText(
                                                this@SignUpActivity,
                                                "Error : $FireBaseRegisterMessage",
                                                Toast.LENGTH_LONG
                                            ).show()
                                            btnRegister.visibility = View.VISIBLE
                                        }
                                    }
                                }.addOnFailureListener {
                                    Toast.makeText(this, "Failed Register", Toast.LENGTH_SHORT).show()
                                }
                            }
                            val intent = Intent(applicationContext, LoginActivity::class.java)
                            intent.putExtra("Email",email)
                            startActivity(intent)
                            this.finish()
                        }
                } else {
                    Toast.makeText(this, "Failed registered", Toast.LENGTH_SHORT).show()
                    progressBarRegister.setVisibility(View.GONE)
                }
            }
    }


    fun clearText() {
        keyinSignupEmail.text = null
        keyinSignupUsername.text = null
        editSignupPassword.text = null
    }

   /* private fun uploadImageToFirebaseStorage(){
        if ( == null) return

        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")

        ref.putFile(!!).addOnSuccessListener {
            Log.d("SignUpActivity", "Successfully uploaded image: ${it.metadata?.path}")


            ref.downloadUrl.addOnSuccessListener {
                Log.d("SignUpActivity", "File Location: $it")

                saveUserToFireDatabase(it.toString())
            }
        }
            .addOnFailureListener{

            }
    }*/

private fun saveUserToFireDatabase(profileImageUrl: String){
    val uid = FirebaseAuth.getInstance().uid ?:""
    val dbref = FirebaseDatabase.getInstance().getReference("/Users/$uid")
    val user = User(uid, keyinSignupUsername.text.toString(), profileImageUrl  )
    dbref.setValue(user)
        .addOnSuccessListener {
            Log.d("SignUpActivity", "Saved to Firebase Database")
        }

}
    class User (val uid:String, val name:String, val profileImageUrl:String)
}




















