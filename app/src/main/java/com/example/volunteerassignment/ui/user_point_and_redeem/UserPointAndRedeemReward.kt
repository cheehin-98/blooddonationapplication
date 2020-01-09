package com.example.volunteerassignment.ui.user_point_and_redeem


import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.volunteerassignment.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.auth.FirebaseAuth



class UserPointAndRedeemReward : Fragment() {


    private lateinit var storage:FirebaseStorage
    private lateinit var ref:FirebaseFirestore
    private lateinit var mAuth: FirebaseAuth

    private lateinit var profileImg: ImageView

    private lateinit var name: TextView
    private lateinit var currPoint: TextView
    private lateinit var  search:EditText

    private lateinit var srchBtn:ImageButton

    private lateinit var prizeList : RecyclerView

    private lateinit var layoutMgr : RecyclerView.LayoutManager
    private lateinit var recyclerViewAdapter : RecycleViewAdapter
    private lateinit var rewardName: ArrayList<String>
    private lateinit var rewardImg: ArrayList<String>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_user_point_and_redeem__reward, container, false)
        mAuth = FirebaseAuth.getInstance()

        val activity = activity as Context

        ref = FirebaseFirestore.getInstance()
        storage = FirebaseStorage.getInstance()

        profileImg = root.findViewById(R.id.profileImg)
        name=root.findViewById(R.id.txtName)
        currPoint=root.findViewById(R.id.txtPoint)
        search=root.findViewById(R.id.editTextSrch)
        srchBtn=root.findViewById(R.id.btnSrch)
        srchBtn.setOnClickListener {


        }

//        val btnCoupon = root.findViewById<Button>(R.id.catCoupon).setOnClickListener {
//
//        }



        rewardImg = arrayListOf()
        rewardName = arrayListOf()

        ref.collection("Reward").get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    rewardName.add(document.get("Name").toString())
                    rewardImg.add(document.id)
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(context, "Unable to retrieve Reward data!", Toast.LENGTH_SHORT).show()
            }

        prizeList = root.findViewById(R.id.prizeRecycle)

        recyclerViewAdapter = RecycleViewAdapter(activity,rewardName,rewardImg)

        prizeList.adapter = recyclerViewAdapter

        layoutMgr = GridLayoutManager(context , 2)
        prizeList.layoutManager = layoutMgr


        loadContent1()
        return root
    }

    override fun onStart() {
        super.onStart()
        val UserRef = ref.collection("Users").document("sample1")

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
    }


}
