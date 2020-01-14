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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList


class ActivityToday : Fragment() {

    private lateinit var eventID: ArrayList<String>
    private lateinit var storage: FirebaseStorage
    private lateinit var ref: FirebaseFirestore
    private lateinit var mAuth: FirebaseAuth

    private lateinit var activityList : RecyclerView

    private lateinit var layoutMgr : RecyclerView.LayoutManager
    private lateinit var activityToday : ActivityToday

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root= inflater.inflate(R.layout.fragment_activity_today, container, false)


        ref = FirebaseFirestore.getInstance()
        storage = FirebaseStorage.getInstance()
        mAuth =  FirebaseAuth.getInstance()

        activityList= root.findViewById(R.id.activityRecycle)
        val c = activity as Context
        eventID = arrayListOf()

        val formatter = DateTimeFormatter.ofPattern("d/M/yyyy", Locale.ENGLISH)
//        val today = LocalDate.parse(LocalDate.now().toString(), formatter)


//        ref.collection("Event_Go").whereEqualTo("UID", "sample1").whereEqualTo("Sign_In", "No")
//            .get()
//            .addOnSuccessListener { documents ->
//                for (doc in documents) {
//
//                    ref.collection("Event").document(doc.get("Event_ID").toString())
//                        .get()
//                        .addOnSuccessListener { document ->
//                            val eventDate = LocalDate.parse(document.get("From Date").toString(), formatter)
//
//                            if(today == eventDate){
//                                eventID.add(doc.get("Event ID").toString())
//                            }
//                        }
//                }
//                layoutMgr = LinearLayoutManager(c)
//                activityList.layoutManager = layoutMgr
//                actViewAdapter = ActViewAdapter(c, eventID)
//                activityList.adapter = actViewAdapter
//
//            }

        return root
    }


}
