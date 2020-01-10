package com.example.volunteerassignment.ui.organization_activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.volunteerassignment.R

class OrganizerGenerateAttedance : AppCompatActivity() {

    private lateinit var tv1: Button
    private lateinit var tv2: Button
    private lateinit var tv3: Button
    private lateinit var tv4: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_organizer_generate_attedance)

        tv1.findViewById<View>(R.id.tv1)
        tv2.findViewById<View>(R.id.tv2)
        tv3.findViewById<View>(R.id.tv3)
        tv4.findViewById<View>(R.id.tv4)
    }

    private fun randomNumber()
    {

    }
}
