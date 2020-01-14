package com.example.volunteerassignment.ui.organization_activity

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.volunteerassignment.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.*

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
    private lateinit var btnUploadEventImage : Button
    private lateinit var btnUpdateEvent : Button
    private lateinit var btnDeleteEvent : Button
    private lateinit var eventImage: ImageView
    private lateinit var chkTermAndCondition: CheckBox
    private lateinit var mImageUri: Uri
    private lateinit var imgID: String
    private val c = Calendar.getInstance()
    private val year = c.get(Calendar.YEAR)
    private val month = c.get(Calendar.MONTH)
    private val day = c.get(Calendar.DAY_OF_MONTH)

    private lateinit var storage: FirebaseStorage
    private lateinit var database: FirebaseFirestore

    val Organizer_UID = "GiagDmqIQJZZOOHPHqhnbAedGLh1"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_organizer_today_event)

        imgID = intent.getStringExtra("imgID")

       /* btnGenAttendance.findViewById<View>(R.id.btnGenerateAttendance)

        btnGenAttendance.setOnClickListener {
            val intent = Intent(this, OrganizerGenerateAttedance::class.java)
            intent.putExtra("imgID", imgID)
            this.startActivity(intent)
        }*/

    }

    override fun onStart() {
        super.onStart()
        fromDate = findViewById(R.id.editEventFromDateu)
        toDate = findViewById(R.id.editEventToDateu)
        fromTime = findViewById(R.id.editEventFromTimeu)
        toTime = findViewById(R.id.editEventToTimeu)
        eventTitle = findViewById(R.id.editEventTitleu)
        numOfParti = findViewById(R.id.editEventNumOfParticipateu)
        point = findViewById(R.id.editEventPointu)
        address = findViewById(R.id.editEventAddressu)
        venue = findViewById(R.id.editEventVenueu)
        description = findViewById(R.id.editEventDescriptionu)
        eventImage = findViewById(R.id.event_imageu)
        btnUploadEventImage = findViewById(R.id.btnUploadEventImageu)
        btnUpdateEvent = findViewById(R.id.btnUpdateEventu)
        btnDeleteEvent = findViewById(R.id.btnDeleteEventu)
        chkTermAndCondition = findViewById(R.id.ckhTermAndConditionu)

        storage = FirebaseStorage.getInstance()
        database = FirebaseFirestore.getInstance()
        val ONE_MEGABYTE = (1024 * 1024).toLong()
        val UserRef = database.collection("Event").document(imgID)
        val rewardRef= storage.reference.child("Event/Organizer_UID/"+ Organizer_UID +"/"+ imgID+".jpg")


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
