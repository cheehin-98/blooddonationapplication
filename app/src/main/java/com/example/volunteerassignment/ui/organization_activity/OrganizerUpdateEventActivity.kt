package com.example.volunteerassignment.ui.organization_activity

import android.app.Activity
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.volunteerassignment.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.lang.Exception
import java.security.AccessController.getContext
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class OrganizerUpdateEventActivity : AppCompatActivity() {
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
    private lateinit var mImageUri:Uri
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
        setContentView(R.layout.activity_organizer_update_event)

         imgID = intent.getStringExtra("imgID")

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

        getEventFromDate()
        getEventToDate()
        getEventFromTime()
        getEventToTime()
        btnUpdateEvent()
        btnUploadEventImage.setOnClickListener {
            pickFromGallery(1)
        }
    }

    override fun onStart() {
        super.onStart()
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


    private fun pickFromGallery(int: Int) {

        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        val mimeTypes = arrayOf("image/jpeg", "image/png")
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        startActivityForResult(intent, int)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(data != null && data.getData() != null && resultCode == Activity.RESULT_OK){

            mImageUri = data.data as Uri

            eventImage.setImageURI(mImageUri)

        }
    }

    private fun getEventFromDate()
    {

        fromDate.setOnClickListener {
            val dpd =
                this?.let { it1 ->
                    DatePickerDialog(it1, DatePickerDialog.OnDateSetListener { view: DatePicker, year:Int, monthOfYear:Int, dayOfMonth:Int ->
                        fromDate.text = "" + dayOfMonth + "/" + (monthOfYear+ 1) + "/" + year
                    }, year, month, day)
                }
            if (dpd != null) {

                dpd.show()
            }
        }
    }

    private fun getEventToDate()
    {
        toDate.setOnClickListener {
            val dpd =
                this?.let { it1 ->
                    DatePickerDialog(it1, DatePickerDialog.OnDateSetListener { view: DatePicker, year:Int, monthOfYear:Int, dayOfMonth:Int ->
                        toDate.text = "" + dayOfMonth + "/" + (monthOfYear+ 1) + "/" + year
                    }, year, month, day)
                }
            if (dpd != null) {
                dpd.show()
            }
        }
    }

    private fun getEventFromTime()
    {
        fromTime.setOnClickListener {

            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                c.set(Calendar.HOUR_OF_DAY, hour)
                c.set(Calendar.MINUTE, minute)
                fromTime.text = SimpleDateFormat("HH:mm").format(c.time)
            }

            TimePickerDialog(this, timeSetListener, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), false).show()
        }

    }

    private fun getEventToTime()
    {
        toTime.setOnClickListener {

            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                c.set(Calendar.HOUR_OF_DAY, hour)
                c.set(Calendar.MINUTE, minute)
                toTime.text = SimpleDateFormat("HH:mm").format(c.time)
            }

            TimePickerDialog(this, timeSetListener, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), false).show()
        }
    }

    private fun btnUpdateEvent()
    {
        btnUpdateEvent.setOnClickListener {
            validateField()
        }

        btnDeleteEvent.setOnClickListener {
            deleteEvent()
        }
    }

    private fun validateField(){

        val setEventTitle = eventTitle.text.toString().trim()
        val setEventFromDate = fromDate.text.toString().trim()
        val setEventToDate = toDate.text.toString().trim()
        val setEventFromTime = fromTime.text.toString().trim()
        val setEventToTime = toTime.text.toString().trim()
        val setEventNumOfParti = numOfParti.text.toString().trim()
        val setEventPoint = point.text.toString().trim()
        val setEventAddress = address.text.toString().trim()
        val setEventVenue = venue.text.toString().trim()
        val setEventDescription = description.text.toString().trim()

        if(setEventTitle.isEmpty())
        {
            eventTitle.requestFocus()
            eventTitle.setError("Field can't be Empty!")
        }

        else if (setEventFromDate.isEmpty())
        {
            fromDate.requestFocus()
            fromDate.setError("Field can't be Empty!")
        }

        else if (setEventToDate.isEmpty())
        {
            toDate.requestFocus()
            toDate.setError("Field can't be Empty!")
        }
        else if (setEventFromTime.isEmpty())
        {
            fromTime.requestFocus()
            fromTime.setError("Field can't be Empty!")
        }
        else if (setEventToTime.isEmpty())
        {
            toTime.requestFocus()
            toTime.setError("Field can't be Empty!")
        }
        else if (setEventNumOfParti.isEmpty())
        {
            numOfParti.requestFocus()
            numOfParti.setError("Field can't be Empty!")
        }
        else if (setEventPoint.isEmpty())
        {
            point.requestFocus()
            point.setError("Field can't be Empty!")
        }
        else if (setEventAddress.isEmpty())
        {
            address.requestFocus()
            address.setError("Field can't be Empty!")
        }
        else if (setEventVenue.isEmpty())
        {
            venue.requestFocus()
            venue.setError("Field can't be Empty!")
        }
        else if (setEventDescription.isEmpty())
        {
            description.requestFocus()
            description.setError("Field can't be Empty!")
        }

        else if (!chkTermAndCondition.isChecked)
        {
            Toast.makeText(this, " Please Confirm the TERM AND CONDITION!", Toast.LENGTH_LONG).show()
            chkTermAndCondition.requestFocus()
        }
        else
        {
            insertDatabase()
        }
    }

    private fun insertDatabase()
    {
        val setEventTitle = eventTitle.text.toString()
        val setEventFromDate = fromDate.text.toString()
        val setEventToDate = toDate.text.toString()
        val setEventFromTime = fromTime.text.toString()
        val setEventToTime = toTime.text.toString()
        val setEventNumOfParti = numOfParti.text.toString()
        val setEventPoint = point.text.toString()
        val setEventAddress = address.text.toString()
        val setEventVenue = venue.text.toString()
        val setEventDescription = description.text.toString()
        var imgID = ""
        try {
            if(chkTermAndCondition.isChecked) {
                val fromDates = fromDate.text.toString()
                val toDates = toDate.text.toString()

                val formatter = DateTimeFormatter.ofPattern("d/M/yyyy", Locale.ENGLISH)
                val fromEventDate = LocalDate.parse(fromDates, formatter)
                val toEventDate = LocalDate.parse(toDates, formatter)

                    if (mImageUri != null) {

                        if (fromEventDate >= toEventDate) {
                            fromDate.requestFocus()
                            fromDate.setError("From Date Not More Than To Date!")

                            val events = HashMap<String, Any>()

                            val progressDialog = ProgressDialog(this)
                            progressDialog.setTitle("Uploading")
                            progressDialog.show()

                            val updateRef = database.collection("Event").document(imgID)
                            updateRef.update("Event Title",setEventTitle)
                            updateRef.update(setEventFromDate, "From Date")
                            updateRef.update(setEventToDate, "To Date")
                            updateRef.update(setEventFromTime, "From Time")
                            updateRef.update(setEventToTime, "To Time")
                            updateRef.update(setEventNumOfParti, "Number of Participate")
                            updateRef.update(setEventPoint, "Point")
                            updateRef.update(setEventAddress, "Address")
                            updateRef.update(setEventVenue, "Venue")
                            updateRef.update(setEventDescription, "Description")
                            events.put("Event Last Update ", LocalDate.now())

                            progressDialog.hide()
                            }
                        }
                    }
            } catch (e: Exception)
                {
                    Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
                }
            return
    }

    private fun deleteEvent()
    {
         database.collection("Event").document(imgID).delete().addOnSuccessListener {
            Toast.makeText(this, "Data deleted !", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(this, "Data Not deleted !", Toast.LENGTH_SHORT).show()
        }

        storage.reference.child("Event/Organizer_UID/"+ Organizer_UID +"/"+ imgID+".jpg").delete()
            .addOnSuccessListener { Toast.makeText(this, "Data deleted !", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener { Toast.makeText(this, "Data Not deleted !", Toast.LENGTH_SHORT).show()  }

    }
}
