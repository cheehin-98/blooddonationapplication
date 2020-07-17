package com.example.blooddonationapplication.ui.organizerEvent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import com.example.blooddonationapplication.R

class OrganizerGenerateAttedance : AppCompatActivity() {

    private lateinit var CodeView: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_organizer_generate_attedance)

        CodeView = findViewById(R.id.tv1)
        val goback: ImageButton = findViewById(R.id.img_btn_back)
        goback.setOnClickListener {
            this.finish()
        }

        val bundle :Bundle ?=intent.extras
        if (bundle!=null) {
            val code = bundle.getString("code")

            CodeView.setText(code)
        }

    }
}
