package com.example.volunteerassignment.ui.user_account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.example.volunteerassignment.R
import com.google.android.material.tabs.TabLayout
import android.content.Intent
import com.google.firebase.storage.FirebaseStorage
import android.app.Activity.RESULT_OK
import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import java.io.ByteArrayOutputStream
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException

class UserAccountFragment : Fragment() {

    private lateinit var userAccountFragment: UserAccountViewModel


    private lateinit var viewpager: ViewPager
    private lateinit var tabLayout: TabLayout

    private lateinit var backImg:ImageView
    private lateinit var profileImg: ImageView

    private lateinit var name:TextView
    private lateinit var currPoint:TextView

    private lateinit var storage: FirebaseStorage
    private lateinit var ref:FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        userAccountFragment =
            ViewModelProviders.of(this).get(UserAccountViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_user_my_account, container, false)

        ref = FirebaseFirestore.getInstance()
        storage = FirebaseStorage.getInstance()

        tabLayout = root.findViewById(R.id.tabLayout)
        viewpager = root.findViewById(R.id.viewPager)
        setupPager()
        integrateTabWithPager()

        profileImg = root.findViewById(R.id.profileImg)
        profileImg.setOnClickListener{
            pickFromGallery(1)
        }

        backImg = root.findViewById(R.id.backImg)
        backImg.setOnClickListener{
            pickFromGallery(2)
        }

        name= root.findViewById(R.id.txtName)
        currPoint=root.findViewById(R.id.txtPoint)

        loadContent()

        return root
    }

    private fun loadContent(){

        val profileStorageRef = storage.reference.child("User/sample1/profile.jpg")
        val backStorageRef= storage.reference.child("User/sample1/background.jpg")

        val ONE_MEGABYTE = (1024 * 1024).toLong()

        profileStorageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener { bytes ->
                val bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                profileImg.setImageBitmap(bmp)
            }
            .addOnFailureListener {
                profileImg.setImageResource(R.drawable.ic_menu_camera)
            }

        backStorageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener { bytes ->
            val bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                backImg.setImageBitmap(bmp)
            }
            .addOnFailureListener {
                backImg.setImageResource(R.drawable.ic_menu_camera)
            }


        ref.collection("Users").document("sample1")//UID
            .get()
            .addOnSuccessListener { documentSnapshot ->
                if(documentSnapshot.exists()){
                    name.append(documentSnapshot.get("Name").toString())
                    currPoint.append(documentSnapshot.get("Point").toString())
                }
                else{
                    Toast.makeText(context, "Unable to retrieve data!", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(context, "Unable to retrieve data!", Toast.LENGTH_SHORT).show()
            }
        //ref.collection("Users").whereEqualTo("Type","Admin")

    }

    private fun pickFromGallery(int: Int) {

        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        val mimeTypes = arrayOf("image/jpeg", "image/png")
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        startActivityForResult(intent, int)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(data != null && data.getData() != null && resultCode == RESULT_OK){

            val dataUri = data.data as Uri

            val storageRef = storage.reference.child("User/sample1")//sample1 should be replace with user login UID

            if(requestCode == 1){
                profileImg.setImageURI(dataUri)

                val profileRef = storageRef.child("profile.jpg")

                profileRef.putFile(dataUri).addOnSuccessListener {
                    Toast.makeText(context, "Image Saved!", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener{
                    Toast.makeText(context, "Image Not Saved!", Toast.LENGTH_SHORT).show()
                }

            }else if(requestCode==2){
                backImg.setImageURI(dataUri)

                val backRef = storageRef.child("background.jpg")
                backRef.putFile(dataUri).addOnFailureListener {
                    Toast.makeText(context, "Image Not Saved!", Toast.LENGTH_SHORT).show()
                }.addOnSuccessListener {
                    Toast.makeText(context, "Image Saved!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    private fun setupPager() {
        viewpager.setAdapter(fragmentPagerAdapter(childFragmentManager))
    }

    private fun integrateTabWithPager() {
        tabLayout.setupWithViewPager(viewpager)
    }

    class fragmentPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager,
        FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){

        override fun getItem(position: Int): Fragment {
            return when (position) {
                1 -> {
                    UserAccountFollow()
                }
                2 ->{
                    UserAccountAttend()
                }
                else -> {
                    UserAccountAchieve()
                }
            }
        }

        override fun getCount(): Int { // number of views
            return 3
        }

        override fun getPageTitle(position: Int): CharSequence? {
            val TAB_TITLES = arrayOf(
                "Achievement",
                "Follow",
                "Attendance"
            )
            return TAB_TITLES[position]
        }
    }
}