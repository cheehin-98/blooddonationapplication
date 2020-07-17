package com.example.blooddonationapplication.ui.donorEvent

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.SearchEvent
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.blooddonationapplication.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_event.*
import java.lang.Exception

class EventFragment : Fragment() {

    private lateinit var storage: FirebaseStorage
    private lateinit var ref: FirebaseFirestore
    private lateinit var eventList : RecyclerView

    private lateinit var layoutMgr : RecyclerView.LayoutManager
    private lateinit var eventViewAdapter : EventViewAdapter
    private lateinit var EventName: ArrayList<String>
    private lateinit var EventImg: ArrayList<String>


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_event, container, false)



        ref = FirebaseFirestore.getInstance()
        storage = FirebaseStorage.getInstance()

        EventName = arrayListOf()
        EventImg= arrayListOf()

        //val searchView = view?.findViewById<SearchView>(R.id.searchEvent)

        val c = activity as Context

        ref.collection("Event").get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    EventName.add(document.get("Event Title").toString())
                    EventImg.add(document.id)
                }
                eventList = root.findViewById(R.id.eventRecycle)
                layoutMgr = GridLayoutManager(context , 2)
                eventList.layoutManager = layoutMgr
                eventViewAdapter = EventViewAdapter(c,EventName,EventImg)
                eventList.adapter = eventViewAdapter

            }
            .addOnFailureListener { exception ->
                Toast.makeText(context, "Unable to retrieve Reward data!", Toast.LENGTH_SHORT).show()
            }


        return root
    }
}