package com.example.blooddonationapplication.ui.organizerEvent


import android.app.Activity
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Range
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.get
import androidx.lifecycle.ViewModelProviders
import com.example.blooddonationapplication.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.lang.Exception
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.HashMap
import kotlin.math.min


class OrganizerCreateEventFragment : Fragment() {

    private lateinit var OrganizerCreateEventFragment: OrganizerActivityViewModel
    private lateinit var fromDate : TextView
    private lateinit var toDate : TextView
    private lateinit var fromTime : TextView
    private lateinit var toTime : TextView
    private lateinit var eventTitle : TextView
    private lateinit var numOfParti : TextView
    //private lateinit var point : TextView
    private lateinit var address : TextView
    private lateinit var venue : TextView
    private lateinit var description : TextView
    private lateinit var btnUploadEventImage : Button
    private lateinit var btnCreateEvent : Button
    private lateinit var eventImage: ImageView
    private lateinit var chkTermAndCondition: CheckBox
    private lateinit var mImageUri: Uri
    private lateinit var createEventDate: Calendar
    private val c = Calendar.getInstance()
    private val year = c.get(Calendar.YEAR)
    private val month = c.get(Calendar.MONTH)
    private val day = c.get(Calendar.DAY_OF_MONTH)
    private lateinit var storage: FirebaseStorage
    private lateinit var database:FirebaseFirestore
    private lateinit var mAuth: FirebaseAuth
    private var organizerUID = "kfRxZs5pAvdiQk8F67ZfrjgaA9q1"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        OrganizerCreateEventFragment =
            ViewModelProviders.of(this).get(OrganizerActivityViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_organization_create_event, container, false)

        fromDate = root.findViewById(R.id.editEventFromDate)
        toDate = root.findViewById(R.id.editEventToDate)
        fromTime = root.findViewById(R.id.editEventFromTime)
        toTime = root.findViewById(R.id.editEventToTime)
        eventTitle = root.findViewById(R.id.editEventTitle)
        numOfParti = root.findViewById(R.id.editEventNumOfParticipate)
       // point = root.findViewById(R.id.editEventPoint)
        address = root.findViewById(R.id.editEventAddress)
        venue = root.findViewById(R.id.editEventVenue)
        description = root.findViewById(R.id.editEventDescription)
        eventImage = root.findViewById(R.id.event_image)
        btnUploadEventImage = root.findViewById(R.id.btnUploadEventImage)
        btnCreateEvent = root.findViewById(R.id.btnCreateEvent)
        chkTermAndCondition = root.findViewById(R.id.ckhTermAndCondition)

        storage = FirebaseStorage.getInstance()
        database = FirebaseFirestore.getInstance()
        mAuth = FirebaseAuth.getInstance()

        getEventFromDate()
        getEventToDate()
        getEventFromTime()
        getEventToTime()
        btnCreateEvent()

        btnUploadEventImage.setOnClickListener {
            pickFromGallery(0)
        }

        return root
    }

    private fun pickFromGallery(int : Int) {

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
                activity?.let { it1 ->
                    DatePickerDialog(it1, DatePickerDialog.OnDateSetListener { view: DatePicker, year:Int, monthOfYear:Int, dayOfMonth:Int ->
                        fromDate.text = "" + dayOfMonth + "/" + (monthOfYear+ 1) + "/" + year
                    }, year, month, day)

                }


            if (dpd != null) {
                dpd.datePicker.minDate = System.currentTimeMillis()- 1000
                dpd.show()


            }
        }
    }

    private fun getEventToDate()
    {
        toDate.setOnClickListener {
            val dpd =
                activity?.let { it1 ->
                    DatePickerDialog(it1, DatePickerDialog.OnDateSetListener { view: DatePicker, year:Int, monthOfYear:Int, dayOfMonth:Int ->
                        toDate.text = "" + dayOfMonth + "/" + (monthOfYear+ 1) + "/" + year
                    }, year, month, day)
                }
            if (dpd != null) {
                dpd.datePicker.minDate = System.currentTimeMillis() - 1000
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
                timePicker. setOnTimeChangedListener { timePicker, hour, minute ->
                    if (hour > 8) {
                        timePicker.setHour(8)
                    } else
                        if (hour < 15) {
                            timePicker.setHour(15)
                        }
                }


            }


            TimePickerDialog(activity, timeSetListener, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), false).show()

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

            TimePickerDialog(activity, timeSetListener, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), false).show()
        }
    }

    private fun btnCreateEvent()
    {
        btnCreateEvent.setOnClickListener {

            validateField()
        }
    }

    private fun validateField(){

        val setEventTitle = eventTitle.text.toString().trim()
        val setEventFromDate = fromDate.text.toString().trim()
        val setEventToDate = toDate.text.toString().trim()
        val setEventFromTime = fromTime.text.toString().trim()
        val setEventToTime = toTime.text.toString().trim()
        val setEventNumOfParti = numOfParti.text.toString().trim()
        //val setEventPoint = point.text.toString().trim()
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
       //else if (setEventPoint.isEmpty())
      //  {
        //    point.requestFocus()
       //     point.setError("Field can't be Empty!")
      //  }
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
            Toast.makeText(context, " Please Confirm the TERM AND CONDITION!", Toast.LENGTH_LONG).show()
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
        //val setEventPoint = point.text.toString()
        val setEventAddress = address.text.toString()
        val setEventVenue = venue.text.toString()
        val setEventDescription = description.text.toString()
        try {
             if(chkTermAndCondition.isChecked) {
                 val fromDates = fromDate.text.toString()
                 val toDates = toDate.text.toString()


                 val formatter = DateTimeFormatter.ofPattern("d/M/yyyy", Locale.ENGLISH)
                 createEventDate = Calendar.getInstance()

                 var eventCreateDate = DateFormat.getDateInstance().format(createEventDate.time)

                 val fromEventDate = LocalDate.parse(fromDates, formatter)
                 val toEventDate = LocalDate.parse(toDates, formatter)
                 try {
                     if (eventImage.drawable != null) {

                         if (fromEventDate <= toEventDate) {

                             val events = HashMap<String, Any>()

                             events.put("Event Title", setEventTitle)
                             events.put("From Date", setEventFromDate)
                             events.put("To Date", setEventToDate)
                             events.put("From Time", setEventFromTime)
                             events.put("To Time", setEventToTime)
                             events.put("Number of Participate", setEventNumOfParti)
                             //events.put("Point", setEventPoint)
                             events.put("Address", setEventAddress)
                             events.put("Venue", setEventVenue)
                             events.put("Description", setEventDescription)
                             events.put("Orgernizer_UID", organizerUID)
                             events.put("Create Event", eventCreateDate)

                             val progressDialog = ProgressDialog(getContext())
                             progressDialog.setTitle("Inserting")
                             progressDialog.show()

                             //Insert
                             var getImgId =  database.collection("Event").document().id
                             database.collection("Event").document(getImgId)
                                 .set(events).addOnSuccessListener {

                                     val storageRef =
                                         storage.reference.child("Event/")
                                     val profileRef = storageRef.child(getImgId+".jpg")
                                     profileRef.putFile(mImageUri)
                                     Toast.makeText(context, " Added!", Toast.LENGTH_SHORT).show()

                                     createAll()
                                     progressDialog.hide()

                                 }.addOnFailureListener {
                                     Toast.makeText(context, "No Add!", Toast.LENGTH_SHORT).show()
                                 }
                         }
                         else
                         {
                             fromDate.requestFocus()
                             fromDate.setError("From Date Not More Than To Date!")
                         }
                     }

                 }catch (e: Exception)
                 {
                     Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
                 }
                 return
             }
        }
        catch (e: Exception)
        {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
        }
        return
    }

    private fun createAll()
    {
         eventTitle.text= ""
        eventTitle.setError(null)
         fromDate.text = ""
         fromDate.setError(null)
         toDate.text = ""
        toDate.setError(null)
         fromTime.text = ""
        fromTime.setError(null)
         toTime.text = ""
        toTime.setError(null)
         numOfParti.text = ""
        numOfParti.setError(null)
      //  point.text = ""
      //  point.setError(null)
         address.text = ""
        address.setError(null)
         venue.text = ""
        venue.setError(null)
         description.text = ""
        description.setError(null)
         eventImage.setImageResource(R.drawable.ic_upload_image)
         chkTermAndCondition.isChecked = false
    }
}

