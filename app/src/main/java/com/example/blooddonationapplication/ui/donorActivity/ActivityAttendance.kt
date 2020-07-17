package com.example.blooddonationapplication.ui.donorActivity

import androidx.appcompat.app.AppCompatActivity
import com.example.blooddonationapplication.R
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ActivityAttendance : AppCompatActivity() {
    private lateinit var ref: FirebaseFirestore
    private lateinit var mAuth: FirebaseAuth
    private lateinit var code: EditText
    private lateinit var sign:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.attendance)
        mAuth = FirebaseAuth.getInstance()
        ref = FirebaseFirestore.getInstance()

        val UID = mAuth.currentUser!!.uid
        val extras = this.intent.extras
        val eventID = extras!!.getString("EventID")
        val eventGoID= extras.getString("EventGoID")
        code=findViewById(R.id.editAttendance)
        sign=findViewById(R.id.btnSignAttendance)

        sign.setOnClickListener {
            if(!code.text.toString().isNullOrEmpty()){

                ref.collection("Event").document(eventID.toString())
                    .get().addOnSuccessListener {documentSnapshot->
                        if(documentSnapshot.get("Attendance Code").toString().isNullOrEmpty()){
                            Toast.makeText(this, "No Generated Attendance code!", Toast.LENGTH_SHORT).show()
                        }else{
                            if(documentSnapshot.get("Attendance Code").toString() == code.text.toString()){
                                val addPoint = documentSnapshot.get("Point").toString().toInt()
                                ref.collection("Event_Go").document(eventGoID.toString()).update("Sign_In", "Yes")
                                    .addOnSuccessListener {
                                        ref.collection("Users").document(UID).update("Point", addPoint)
                                            .addOnSuccessListener {
                                                this.finish()
                                            }
                                    }.addOnFailureListener{
                                        Toast.makeText(this, "Fail to update Event GO!", Toast.LENGTH_SHORT).show()
                                    }
                            }else{
                                Toast.makeText(this, "Wrong Attendance code!", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
            }else{
                Toast.makeText(this, "Please key in your attendance code!", Toast.LENGTH_SHORT).show()
            }

        }


    }
}
