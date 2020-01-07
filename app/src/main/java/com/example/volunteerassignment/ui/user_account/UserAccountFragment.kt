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
import android.graphics.drawable.BitmapDrawable
import java.io.ByteArrayOutputStream
import android.net.Uri

/**
 * A simple [Fragment] subclass.
 */
class UserAccountFragment : Fragment() {

    private lateinit var UserAccountFragment: UserAccountViewModel


    private lateinit var viewpager: ViewPager
    private lateinit var tabLayout: TabLayout


    private lateinit var ProfileImg: ImageView

    private val GALLERY_REQUEST_CODE = 1

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
            pickFromGallery()
        }

        return root
    }

    private fun pickFromGallery() {

        val intent = Intent(Intent.ACTION_PICK)

        intent.type = "image/*"

        val mimeTypes = arrayOf("image/jpeg", "image/png")
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)

        startActivityForResult(intent, GALLERY_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == GALLERY_REQUEST_CODE &&  data != null && data.getData() != null && resultCode == RESULT_OK){
            val dataUri = data.data as Uri
            //val bitmapImag = data.extras as Bitmap
            ProfileImg.setImageURI(dataUri)

            storage = FirebaseStorage.getInstance()

            val storageRef = storage.reference

            // Create a reference to "mountains.jpg"
            val mountainsRef = storageRef.child("profile.jpg")

            // Create a reference to 'images/mountains.jpg'
            val mountainImagesRef = storageRef.child("images/profile.jpg")

            // While the file names are the same, the references point to different files
            mountainsRef.name == mountainImagesRef.name // true
            mountainsRef.path == mountainImagesRef.path // false

            val bitmap = (ProfileImg.drawable as BitmapDrawable).bitmap
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val data = baos.toByteArray()

            var uploadTask = mountainsRef.putBytes(data)

            uploadTask.addOnFailureListener {
                // Handle unsuccessful uploads
            }.addOnSuccessListener {
                // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
                // ...
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