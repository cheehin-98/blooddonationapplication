package com.example.volunteerassignment.ui.user_account


import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import com.example.volunteerassignment.R
import com.google.firebase.firestore.FirebaseFirestore

class UserAccountAchieve : Fragment() {
    private lateinit var ref: FirebaseFirestore

    private lateinit var trophy:ImageView
    private lateinit var share: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root =inflater.inflate(R.layout.fragment_user_account_achieve, container, false)

        trophy = root.findViewById(R.id.trophiesImg)

        ref = FirebaseFirestore.getInstance()
        val pointRef = ref.document("Users/sample1")
        //if event prticipte more 20
        trophy.setImageResource(R.drawable.bronze_trophy)

        val builder: StrictMode.VmPolicy.Builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())

        share = root.findViewById(R.id.btnShare)
        share.setOnClickListener {
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(
                Intent.EXTRA_TEXT,
                "Achievement"
            )
            sendIntent.type = "text/plain"
            startActivity(sendIntent)
        }

        return root
    }


}
