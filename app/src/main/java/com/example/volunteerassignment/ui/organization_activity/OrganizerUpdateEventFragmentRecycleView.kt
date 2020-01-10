package com.example.volunteerassignment.ui.organization_activity


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
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

class OrganizerUpdateEventFragmentRecycleView : Fragment() {

    private lateinit var OrganizerUpdateEventFragmentRecycleView: OrganizerActivityViewModel
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var layoutMgr : RecyclerView.LayoutManager
    private lateinit var recyclerViewAdapter : RecycleViewAdapter

    private lateinit var eventImage: ArrayList<String>
    private lateinit var EventTitle: ArrayList<String>

    private lateinit var mAuth: FirebaseAuth
    private lateinit var storage: FirebaseStorage
    private lateinit var database:FirebaseFirestore

    private lateinit var btntesting: Button

    private val organizerUID = "wf7aHafXMEP9kpTQZq9QhhqCf9y2"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_organization_update_event_recycleview, container, false)
        mAuth = FirebaseAuth.getInstance()
        val activity = activity as Context

        storage = FirebaseStorage.getInstance()
        database = FirebaseFirestore.getInstance()//.document("Event/" + organizerUID)
        mRecyclerView = root.findViewById(R.id.organization_update_event_recycleView)

        eventImage = arrayListOf()
        EventTitle = arrayListOf()

        database.collection("Event").get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    var  chkToDate = document.get("To Date").toString()


                    val formatter = DateTimeFormatter.ofPattern("d/M/yyyy", Locale.ENGLISH)
                    val toEventDate = LocalDate.parse(chkToDate, formatter)

                    if(toEventDate > LocalDate.now())
                    {
                        EventTitle.add(document.get("Event Title").toString())
                        eventImage.add(document.id)
                    }
                }
            }
            .addOnFailureListener {
                Toast.makeText(context, "Unable to retrieve Reward data!", Toast.LENGTH_SHORT).show()
            }

        mRecyclerView = root.findViewById(R.id.organization_update_event_recycleView)
        layoutMgr = LinearLayoutManager(context)
        mRecyclerView.layoutManager = layoutMgr
        recyclerViewAdapter = RecycleViewAdapter(activity,EventTitle,eventImage)
        recyclerViewAdapter.notifyDataSetChanged()
        mRecyclerView.adapter = recyclerViewAdapter


        return root
    }

    /*override fun onStart() {
        super.onStart()
        val UserRef = database.collection("Users").document("sample1")

        UserRef.addSnapshotListener{ snapshot, e ->
            if(snapshot!!.exists()){
                name.setText(getString(R.string.name))
                name.append(snapshot.get("Name").toString())
                currPoint.setText(getText(R.string.currPoint))
                currPoint.append(snapshot.get("Point").toString())
            }else{
                Toast.makeText(context, "Unable to retrieve data!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadContent1(){
        val profileStorageRef = storage.reference.child("User/sample1/profile.jpg")//sample1 is UID

        val ONE_MEGABYTE = (1024 * 1024).toLong()

        profileStorageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener { bytes ->
            val bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            profileImg.setImageBitmap(bmp)
        }
            .addOnFailureListener {
                profileImg.setImageResource(R.drawable.ic_menu_camera)
            }
    }*/
}
