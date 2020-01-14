package com.example.volunteerassignment.ui.organization_activity

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.volunteerassignment.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.security.SecureRandom
import java.util.*
import kotlin.experimental.and

class OrganizerTodayEventActivity : AppCompatActivity() {

   // private lateinit var btnGenAttendance: Button
    private lateinit var fromDate : TextView
    private lateinit var toDate : TextView
    private lateinit var fromTime : TextView
    private lateinit var toTime : TextView
    private lateinit var eventTitle : TextView
    private lateinit var numOfParti : TextView
    private lateinit var point : TextView
    private lateinit var address : TextView
    private lateinit var venue : TextView
    private lateinit var description : TextView
    private lateinit var eventImage: ImageView
    private lateinit var btnGenAttendance: Button
    //private lateinit var btnBack: Button
    private lateinit var storage: FirebaseStorage
    private lateinit var database:FirebaseFirestore
    //private lateinit var mAuth: FirebaseAuth

    val Organizer_UID = "GiagDmqIQJZZOOHPHqhnbAedGLh1"

    val STRING_LENGTH = 6
    val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_organizer_today_event)

        btnGenAttendance = findViewById(R.id.btnGenerateAttendance)

        val goback: ImageButton = findViewById(R.id.img_btn_backTodayEvent)
        goback.setOnClickListener {
            this.finish()
        }
        btnGenAttendance.setOnClickListener {
            genAttendanceCode()
        }
    }

   private  fun generateAttendanceCode() {
        val random = SecureRandom()
        val bytes = ByteArray(STRING_LENGTH)
        random.nextBytes(bytes)

        val randomString = (0..bytes.size - 1)
            .map { i ->
                charPool.get((bytes[i] and 0xFF.toByte() and (charPool.size - 1).toByte()).toInt())
            }.joinToString("")
    }

    private fun genAttendanceCode() {

        val bundle :Bundle ?=intent.extras
        if (bundle!=null) {
            val imgID = bundle.getString("imgID")

            val getRef =  database.collection("Event").document(imgID.toString())

            generateAttendanceCode()
            val randomString = (1..STRING_LENGTH)
                .map { i -> kotlin.random.Random.nextInt(0, charPool.size) }
                .map(charPool::get)
                .joinToString("")
            var code = randomString

            val updateRef = database.collection("Event").document(imgID.toString())
            updateRef.update("Attendance Code", code)

                    val intent = Intent(this, OrganizerGenerateAttedance::class.java)
                    intent.putExtra("code", code)
                    this.startActivity(intent)
                    finish()
        }
    }

    override fun onStart() {
        super.onStart()
        val bundle :Bundle ?=intent.extras
        if (bundle!=null) {
            val imgID = bundle.getString("imgID")

            fromDate = findViewById(R.id.editEventFromDatet)
            toDate = findViewById(R.id.editEventToDatet)
            fromTime = findViewById(R.id.editEventFromTimet)
            toTime = findViewById(R.id.editEventToTimet)
            eventTitle = findViewById(R.id.editEventTitlet)
            numOfParti = findViewById(R.id.editEventNumOfParticipatet)
            point = findViewById(R.id.editEventPointt)
            address = findViewById(R.id.editEventAddresst)
            venue = findViewById(R.id.editEventVenuet)
            description = findViewById(R.id.editEventDescriptiont)
            eventImage = findViewById(R.id.event_imaget)


            storage = FirebaseStorage.getInstance()
            database = FirebaseFirestore.getInstance()
            val ONE_MEGABYTE = (1024 * 1024).toLong()
            val UserRef = database.collection("Event").document(imgID.toString())
            val rewardRef= storage.reference.child("Event/"+ imgID+".jpg")


            rewardRef.getBytes(ONE_MEGABYTE).addOnSuccessListener { bytes ->
                val bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                eventImage.setImageBitmap(bmp)
            }
                .addOnFailureListener {
                    eventImage.setImageResource(R.drawable.ic_menu_camera)
                }


            UserRef.addSnapshotListener{ snapshot, e ->
                if(snapshot!!.exists()){
                    fromDate.append(snapshot.get("From Date").toString())
                    toDate.append(snapshot.get("To Date").toString())
                    fromTime.append(snapshot.get("From Time").toString())
                    toTime.append(snapshot.get("To Time").toString())
                    eventTitle.append(snapshot.get("Event Title").toString())
                    numOfParti.append(snapshot.get("Number of Participate").toString())
                    point.append(snapshot.get("Point").toString())
                    address.append(snapshot.get("Address").toString())
                    venue.append(snapshot.get("Venue").toString())
                    description.append(snapshot.get("Description").toString())

                }

                else{
                    Toast.makeText(this, "Unable to retrieve data!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
