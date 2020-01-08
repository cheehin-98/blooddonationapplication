package com.example.volunteerassignment.ui.user_point_and_redeem

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.example.volunteerassignment.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

import kotlinx.android.synthetic.main.activity_prize_detail.*

class PrizeDetailActivity : AppCompatActivity() {

    private lateinit var storage: FirebaseStorage
    private lateinit var ref: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prize_detail)

        ref = FirebaseFirestore.getInstance()
        storage = FirebaseStorage.getInstance()



    }

}
