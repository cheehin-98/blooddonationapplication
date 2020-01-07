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
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import java.io.ByteArrayOutputStream
import android.net.Uri
import android.widget.Toast

class UserAccountFragment : Fragment() {

    private lateinit var UserAccountFragment: UserAccountViewModel


    private lateinit var viewpager: ViewPager
    private lateinit var tabLayout: TabLayout

    private lateinit var BackImg:ImageView
    private lateinit var ProfileImg: ImageView

    private lateinit var name:TextView
    private lateinit var currPoint:TextView

    private lateinit var storage: FirebaseStorage

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        UserAccountFragment =
            ViewModelProviders.of(this).get(UserAccountViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_user_my_account, container, false)

        tabLayout = root.findViewById(R.id.tabLayout)
        viewpager = root.findViewById(R.id.viewPager)
        setupPager()
        integrateTabWithPager()

        ProfileImg = root.findViewById(R.id.profileImg)
        ProfileImg.setOnClickListener{
            pickFromGallery(1)
        }

        BackImg = root.findViewById(R.id.backImg)
        BackImg.setOnClickListener{
            pickFromGallery(2)
        }

        name= root.findViewById(R.id.txtName)
        currPoint=root.findViewById(R.id.txtPoint)


        loadContent()

        return root
    }

    private fun loadContent(){
        storage = FirebaseStorage.getInstance()

        val profileStorageRef = storage.reference.child("User/sample1/profile.jpg")
        val backStorageRef= storage.reference.child("User/sample1/background.jpg")

        val ONE_MEGABYTE = (1024 * 1024).toLong()

        profileStorageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener { bytes ->
                val bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                ProfileImg.setImageBitmap(bmp)
            }
            .addOnFailureListener {

            }
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
            storage = FirebaseStorage.getInstance()//like establish connection

            val storageRef = storage.reference.child("User/sample1")//sample1 should be replace with user login UID

            if(requestCode == 1){
                ProfileImg.setImageURI(dataUri)

                val profileRef = storageRef.child("profile.jpg")
//            val mountainImagesRef = storageRef.child("User/profile.jpg")
//            mountainsRef.name == mountainImagesRef.name
//            mountainsRef.path == mountainImagesRef.path
                val bitmap = (ProfileImg.drawable as BitmapDrawable).bitmap
                val baos = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                val data = baos.toByteArray()

                var uploadTask = profileRef.putBytes(data)

                uploadTask.addOnFailureListener {
                    Toast.makeText(context, "Image Not Saved!", Toast.LENGTH_SHORT).show()
                }.addOnSuccessListener {
                    Toast.makeText(context, "Image Saved!", Toast.LENGTH_SHORT).show()
                }

            }else if(requestCode==2){
                BackImg.setImageURI(dataUri)

                val backRef = storageRef.child("background.jpg")

                val bitmap = (BackImg.drawable as BitmapDrawable).bitmap
                val baos = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                val data = baos.toByteArray()

                var uploadTask = backRef.putBytes(data)

                uploadTask.addOnFailureListener {
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