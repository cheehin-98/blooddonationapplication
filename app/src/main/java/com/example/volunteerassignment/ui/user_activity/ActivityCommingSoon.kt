package com.example.volunteerassignment.ui.user_activity


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.volunteerassignment.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.*
import kotlin.collections.ArrayList
import java.text.SimpleDateFormat


class ActivityCommingSoon : Fragment() {

    private lateinit var eventID: ArrayList<String>
    private lateinit var storage: FirebaseStorage
    private lateinit var ref: FirebaseFirestore

    private lateinit var activityList : RecyclerView

    private lateinit var layoutMgr : RecyclerView.LayoutManager
    private lateinit var actViewAdapter : ActViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root =  inflater.inflate(R.layout.fragment_activity_comming_soon, container, false)
        ref = FirebaseFirestore.getInstance()
        storage = FirebaseStorage.getInstance()
        activityList= root.findViewById(R.id.activityRecycle)
        val c = activity as Context

        eventID = arrayListOf()

        ref.collection("Event_Go").whereEqualTo("UID", "sample1").whereEqualTo("Sign_In", "No")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {

                    ref.collection("Event").document(document.get("Event_ID").toString())
                        .get()
                        .addOnSuccessListener { document ->

                            val sdf = SimpleDateFormat("dd-MM-yyyy")
                            val date = sdf.parse(document.get("From Date").toString())
                            val cal = Calendar.getInstance()
                            cal.time = date
                             val c = Calendar.getInstance()
                             val year = c.get(Calendar.YEAR)
                             val month = c.get(Calendar.MONTH)
                             val day = c.get(Calendar.DAY_OF_MONTH)
                                println(day.toString())
                            println(cal.time.toString())
                            //
                        }

                }

//                layoutMgr = LinearLayoutManager(c)
//                activityList.layoutManager = layoutMgr
//                actViewAdapter = ActViewAdapter(c, eventID)
//                activityList.adapter = actViewAdapter

            }


        return root
    }


}
