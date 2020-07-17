package com.example.blooddonationapplication.ui.donorAccount

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.blooddonationapplication.R
import com.google.firebase.firestore.FirebaseFirestore

class editProfile : AppCompatActivity() {

    private lateinit var name : TextView
    private lateinit var age : TextView
    private lateinit var phoneNum : TextView


    private lateinit var btnSave : Button
    private lateinit var btnCancel : Button

    private lateinit var ref: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        ref = FirebaseFirestore.getInstance()

        name = findViewById(R.id.editTxtName)
        age = findViewById(R.id.editAge)
        phoneNum = findViewById(R.id.editTxtPhone)

        btnSave = findViewById(R.id.btnSave)
        btnSave.setOnClickListener {
            val profile = hashMapOf(
                "Name" to name.text.toString(),
                "Age" to age.text.toString().toInt(),
                "Phone No" to phoneNum.text.toString()

                )
            ref.collection("Users").document("sample1")
                .set(profile)
        }

        btnCancel = findViewById(R.id.btnCancel)
        btnCancel.setOnClickListener {
            this.finish()
        }



    }
}
