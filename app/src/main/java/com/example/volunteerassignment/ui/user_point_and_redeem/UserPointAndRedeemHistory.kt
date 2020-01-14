package com.example.volunteerassignment.ui.user_point_and_redeem


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.volunteerassignment.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage


class UserPointAndRedeemHistory : Fragment() {


    private lateinit var rewardName:ArrayList<String>
    private lateinit var rewardPoint: ArrayList<String>
    private lateinit var rewardDate: ArrayList<String>
    private lateinit var rewardID: ArrayList<String>

    private lateinit var storage: FirebaseStorage
    private lateinit var ref: FirebaseFirestore
    private lateinit var mAuth: FirebaseAuth

    private lateinit var prizeHisList : RecyclerView

    private lateinit var layoutMgr : RecyclerView.LayoutManager
    private lateinit var listViewAdapter : ListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_user_point_and_redeem__history, container, false)

        ref = FirebaseFirestore.getInstance()
        storage = FirebaseStorage.getInstance()
        mAuth = FirebaseAuth.getInstance()

        val context = activity as Context
        prizeHisList= root.findViewById(R.id.prizeHisRecycle)
        rewardName = arrayListOf()
        rewardPoint = arrayListOf()
        rewardDate = arrayListOf()
        rewardID = arrayListOf()

        return root
    }

    override fun onResume() {
        super.onResume()
        val c = activity as Context
        rewardName.clear()
        rewardPoint.clear()
        rewardDate.clear()
        rewardID.clear()
        val currentuser = mAuth.currentUser
        ref.collection("History_Reward").whereEqualTo("UID",currentuser?.uid).get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    rewardName.add(document.get("Name").toString())
                    rewardPoint.add(document.get("Point_Used").toString())
                    rewardDate.add(document.get("Redeem_Date").toString())
                    rewardID.add(document.get("Reward_ID").toString())
                }
                layoutMgr = LinearLayoutManager(c)
                prizeHisList.layoutManager = layoutMgr
                listViewAdapter = ListAdapter(c,rewardName,rewardPoint,rewardDate, rewardID)
                listViewAdapter.notifyDataSetChanged()
                prizeHisList.adapter = listViewAdapter
            }
            .addOnFailureListener { exception ->
                Toast.makeText(c, "No History data!", Toast.LENGTH_SHORT).show()
            }
    }


}
