package com.example.blooddonationapplication.ui.organizerEvent


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.blooddonationapplication.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlin.collections.ArrayList

class OrganizerEventOnTodayFragmentRecycleView : Fragment() {

    private lateinit var OrganizerUpdateEventFragmentRecycleView: OrganizerActivityViewModel
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var layoutMgr : RecyclerView.LayoutManager
    private lateinit var recyclerViewAdapter : RecycleViewAdapters

    private lateinit var eventImage: ArrayList<String>
    private lateinit var eventTitle: ArrayList<String>
    private lateinit var eventFromDate: ArrayList<String>
    private lateinit var eventToDate: ArrayList<String>

    private lateinit var mAuth: FirebaseAuth
    private lateinit var storage: FirebaseStorage
    private lateinit var database: FirebaseFirestore

    private val organizerUID = "kfRxZs5pAvdiQk8F67ZfrjgaA9q1"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_organization_event_on_today_recycleview, container, false)
        mAuth = FirebaseAuth.getInstance()
        val activity = activity as Context

        storage = FirebaseStorage.getInstance()
        database = FirebaseFirestore.getInstance()
        mRecyclerView = root.findViewById(R.id.organization_Today_event_recycle_view)

        eventImage = arrayListOf()
        eventTitle = arrayListOf()
        eventFromDate = arrayListOf()
        eventToDate = arrayListOf()


        mRecyclerView = root.findViewById(R.id.organization_Today_event_recycle_view)
        layoutMgr = LinearLayoutManager(context)
        mRecyclerView.layoutManager = layoutMgr
        recyclerViewAdapter = RecycleViewAdapters(activity,eventTitle,eventImage, eventFromDate, eventToDate)
        recyclerViewAdapter.notifyDataSetChanged()
        mRecyclerView.adapter = recyclerViewAdapter


        return root
    }

    override fun onResume() {
        super.onResume()
        clearArrayList()
        val activity = activity as Context
        database.collection("Event").get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    if(document.id != null)
                    {
                        /*if( document.get("From Date").toString().isNotBlank())
                        {
                            var  chkFromDate = document.get("From Date").toString()

                            val formatter = DateTimeFormatter.ofPattern("d/M/yyyy", Locale.ENGLISH)
                            val getFromDate = LocalDate.parse(chkFromDate, formatter)

                            if(getFromDate < LocalDate.now())
                            {*/
                        eventTitle.add(document.get("Event Title").toString())
                        eventFromDate.add(document.get("From Date").toString())
                        eventToDate.add(document.get("To Date").toString())
                        eventImage.add(document.id)

                        /*  }
                          else {
                              Toast.makeText(context, "Unable to retrieve data 1 !", Toast.LENGTH_SHORT).show()
                          }
                      }
                      else {
                          Toast.makeText(context, "Unable to retrieve data 2!", Toast.LENGTH_SHORT).show()
                      }
                  }
                  else {
                      Toast.makeText(context, "No record!", Toast.LENGTH_SHORT).show()*/
                    }
                }
                layoutMgr = LinearLayoutManager(context)
                mRecyclerView.layoutManager = layoutMgr
                recyclerViewAdapter = RecycleViewAdapters(activity,eventTitle,eventImage,eventFromDate,eventToDate)
                recyclerViewAdapter.notifyDataSetChanged()
                mRecyclerView.adapter = recyclerViewAdapter

            }.addOnFailureListener {
                Toast.makeText(context, "Unable to retrieve data 3!", Toast.LENGTH_SHORT).show()
            }
    }
    private fun clearArrayList()
    {
        eventTitle.clear()
        eventFromDate.clear()
        eventToDate.clear()
        eventImage.clear()
    }

}
