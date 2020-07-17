package com.example.blooddonationapplication.ui.donorActivity


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.blooddonationapplication.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlin.collections.ArrayList


class ActivityCommingSoon : Fragment() {

    private lateinit var eventID: ArrayList<String>
    private lateinit var eventGoID: ArrayList<String>
    private lateinit var storage: FirebaseStorage
    private lateinit var ref: FirebaseFirestore
    private lateinit var mAuth: FirebaseAuth

    private lateinit var activityList : RecyclerView

    private lateinit var layoutMgr : RecyclerView.LayoutManager
    private lateinit var activityCommingSoon: ActivityCommingSoonAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root =  inflater.inflate(R.layout.fragment_activity_comming_soon, container, false)
        ref = FirebaseFirestore.getInstance()
        storage = FirebaseStorage.getInstance()
        mAuth =  FirebaseAuth.getInstance()

        activityList= root.findViewById(R.id.activityRecycle)
        val c = activity as Context

        eventID = arrayListOf()
        eventGoID = arrayListOf()


        val currentuser = mAuth.currentUser
        ref.collection("Event_Go").whereEqualTo("UID", currentuser?.uid).whereEqualTo("Sign_In", "No")
            .get()
            .addOnSuccessListener { documents ->
                for (doc in documents) {
                    eventID.add(doc.get("Event_ID").toString())
                    eventGoID.add(doc.id)

                }
                layoutMgr = LinearLayoutManager(c)
                activityList.layoutManager = layoutMgr
                activityCommingSoon = ActivityCommingSoonAdapter(c, eventID,eventGoID)
                activityList.adapter = activityCommingSoon

            }
        return root
    }


}
