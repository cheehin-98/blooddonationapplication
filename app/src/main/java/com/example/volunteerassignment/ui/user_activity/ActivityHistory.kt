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


class ActivityHistory : Fragment() {

    private lateinit var eventID: ArrayList<String>

    private lateinit var storage: FirebaseStorage
    private lateinit var ref: FirebaseFirestore
    private lateinit var mAuth: FirebaseAuth

    private lateinit var activityList: RecyclerView

    private lateinit var layoutMgr: RecyclerView.LayoutManager
    private lateinit var actViewAdapter: ActivityHistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_activity_history, container, false)

        ref = FirebaseFirestore.getInstance()
        storage = FirebaseStorage.getInstance()
        mAuth =  FirebaseAuth.getInstance()

        activityList = root.findViewById(R.id.activityRecycle)
        eventID = arrayListOf()

        val c = activity as Context

        val currentuser = mAuth.currentUser
        ref.collection("Event_Go").whereEqualTo("UID", currentuser?.uid).whereEqualTo("Sign_In", "Yes")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    eventID.add(document.get("Event_ID").toString())
                }

                layoutMgr = LinearLayoutManager(c)
                activityList.layoutManager = layoutMgr
                actViewAdapter = ActivityHistoryAdapter(c, eventID)
                activityList.adapter = actViewAdapter

            }
        return root
    }

    override fun onResume() {
        super.onResume()
        val c = activity as Context

        val currentuser = mAuth.currentUser
        eventID.clear()
        ref.collection("Event_Go").whereEqualTo("UID", currentuser?.uid).whereEqualTo("Sign_In", "Yes")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    eventID.add(document.get("Event_ID").toString())
                }

                layoutMgr = LinearLayoutManager(c)
                activityList.layoutManager = layoutMgr
                actViewAdapter = ActivityHistoryAdapter(c, eventID)
                activityList.adapter = actViewAdapter

            }
    }
}
