package com.example.volunteerassignment.ui.user_point_and_redeem

import android.content.ContentValues.TAG
import android.graphics.BitmapFactory
import android.net.sip.SipSession
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.volunteerassignment.R
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.squareup.okhttp.internal.DiskLruCache


class UserPointAndRedeem_Reward : Fragment() {


    private lateinit var storage:FirebaseStorage
    private lateinit var ref:FirebaseFirestore

    private lateinit var ProfileImg: ImageView

    private lateinit var name: TextView
    private lateinit var currPoint: TextView
    private lateinit var  search:EditText

    private lateinit var UserRef:DocumentReference

    private lateinit var recyclerView: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_user_point_and_redeem__reward, container, false)

        ref = FirebaseFirestore.getInstance()
        storage = FirebaseStorage.getInstance()

        ProfileImg = root.findViewById(R.id.profileImg)

        name=root.findViewById(R.id.txtName)
        currPoint=root.findViewById(R.id.txtPoint)
        search=root.findViewById(R.id.editTextSrch)

        loadContent1()
        return root
    }

    override fun onResume() {
        super.onResume()
        ref = FirebaseFirestore.getInstance()
        UserRef = ref.document("Users/sample1")

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
                ProfileImg.setImageBitmap(bmp)
            }
            .addOnFailureListener {
                ProfileImg.setImageResource(R.drawable.ic_menu_camera)
            }

//        ref.document("Users/sample1")
//            .get()
//            .addOnSuccessListener { documentSnapshot ->
//                if(documentSnapshot.exists()){
//                    name.setText(getString(R.string.name))
//                    name.append(documentSnapshot.get("Name").toString())
//                    currPoint.setText(getText(R.string.currPoint))
//                    currPoint.append(documentSnapshot.get("Point").toString())
//                }
//                else{
//                    Toast.makeText(context, "Unable to retrieve data!", Toast.LENGTH_SHORT).show()
//                }
//            }
//            .addOnFailureListener { exception ->
//                Toast.makeText(context, "Unable to retrieve data!", Toast.LENGTH_SHORT).show()
//            }
    }


}
