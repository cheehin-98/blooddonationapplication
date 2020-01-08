package com.example.volunteerassignment.ui.organization_activity


import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.text.DateFormat
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.volunteerassignment.R
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class OrganizerActivityFragment : Fragment() {

    private lateinit var OrganizerActivityFragment: OrganizerActivityViewModel
    private  lateinit var fromDate : TextView
    private  lateinit var toDate : TextView
    private  lateinit var fromTime : TextView
    private  lateinit var toTime : TextView
    private  lateinit var eventTitle : TextView
    private  lateinit var btnCreateEvent : Button

    private val c = Calendar.getInstance()
    private val year = c.get(Calendar.YEAR)
    private val month = c.get(Calendar.MONTH)
    private val day = c.get(Calendar.DAY_OF_MONTH)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        OrganizerActivityFragment =
            ViewModelProviders.of(this).get(OrganizerActivityViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_organization_my_activity, container, false)

        fromDate = root.findViewById(R.id.editEventFromDate)
        toDate = root.findViewById(R.id.editEventToDate)
        fromTime = root.findViewById(R.id.editEventFromTime)
        toTime = root.findViewById(R.id.editEventToTime)
        eventTitle = root.findViewById(R.id.editEventTitle)
        btnCreateEvent = root.findViewById(R.id.btnCreateEvent)
        //Get.Set here
        //val textView: TextView = root.findViewById(R.id.text_organization_my_activity)

        OrganizerActivityFragment.text.observe(this, Observer {
            //textView.text = it
        })

        getEventFromDate()
        getEventToDate()
        getEventFromTime()
        getEventToTime()
        btnCreateEvent()

        return root
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
         /*   val fromDate = fromDate.text.toString()
            val toDate = toDate.text.toString()

            val formatter = DateTimeFormatter.ofPattern("d/M/yyyy", Locale.ENGLISH)
            val fromEventDate = LocalDate.parse(fromDate, formatter)
            val toEventDate = LocalDate.parse(toDate, formatter)

          if(fromEventDate < toEventDate)
            {
                Toast.makeText(activity, "Correct", Toast.LENGTH_SHORT).show()
           }
            else
          {
              Toast.makeText(activity, "InCorrect", Toast.LENGTH_SHORT).show()
          }*/
        }
    }

    private fun validateField(){
        val setEventTitle = eventTitle.text.toString().trim()

        if(setEventTitle.isEmpty())
        {
            eventTitle.requestFocus()
            eventTitle.setError("Field can't be Empty!")

        }
    }
}
