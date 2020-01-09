package com.example.volunteerassignment.ui.user_point_and_redeem


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.volunteerassignment.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage


class UserPointAndRedeemHistory : Fragment() {


    private lateinit var prizeHis:ArrayList<String>

    private lateinit var storage: FirebaseStorage
    private lateinit var ref: FirebaseFirestore

    private lateinit var prizeHisList : RecyclerView

    private lateinit var layoutMgr : RecyclerView.LayoutManager
    private lateinit var recyclerViewAdapter : RecycleViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_user_point_and_redeem__history, container, false)

        ref = FirebaseFirestore.getInstance()
        storage = FirebaseStorage.getInstance()

        prizeHisList= root.findViewById(R.id.prizeHisRecycle)
        //layoutManager = GridLayoutManager(this , 2)

        //val historyRef = ref.document("Users/sample1/History")
        return root
    }


}
