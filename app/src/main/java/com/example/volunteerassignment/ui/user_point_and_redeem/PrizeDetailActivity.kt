package com.example.volunteerassignment.ui.user_point_and_redeem

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.volunteerassignment.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.*


class PrizeDetailActivity : AppCompatActivity() {

    private lateinit var storage: FirebaseStorage
    private lateinit var ref: FirebaseFirestore
    private lateinit var mAuth: FirebaseAuth

    private lateinit var imgPrize : ImageView
    private lateinit var name : TextView
    private lateinit var requiredPoint:TextView
    private lateinit var category:TextView
    private lateinit var redeemBtn:Button
    var currPoint =0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prize_detail)

        ref = FirebaseFirestore.getInstance()
        storage = FirebaseStorage.getInstance()
        mAuth =  FirebaseAuth.getInstance()

        imgPrize = findViewById(R.id.prizeImage)
        name = findViewById(R.id.txtName)
        requiredPoint = findViewById(R.id.txtPoint)
        category = findViewById(R.id.txtCat)
        val extras = this.intent.extras
        val prizeID = extras!!.getString("prizeID")
        val call = extras.getString("Call")



        val rewardStorageRef = storage.reference.child("Prize/" + prizeID.toString() + ".jpg")

        val ONE_MEGABYTE = (1024 * 1024).toLong()

        rewardStorageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener { bytes ->
            val bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            imgPrize.setImageBitmap(bmp)
            }
            .addOnFailureListener {
                imgPrize.setImageResource(R.drawable.ic_menu_camera)
            }



        ref.document("Reward/" + prizeID.toString())
            .get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    name.text =(documentSnapshot.get("Name").toString())
                    requiredPoint.text=(documentSnapshot.get("Point").toString())
                    category.text = (documentSnapshot.get("Category").toString())
                } else {
                    Toast.makeText(this, "Unable to retrieve data!", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Unable to retrieve data!", Toast.LENGTH_SHORT).show()
            }

        val UID = mAuth.currentUser!!.uid

        ref.collection("Users").document(UID)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    currPoint = documentSnapshot.get("Point").toString().toInt()
                } else {
                    Toast.makeText(this, "Unable to retrieve data!", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Unable to retrieve data!", Toast.LENGTH_SHORT).show()
            }

        redeemBtn = findViewById(R.id.btnRedeem)

        if(call=="History"){
            redeemBtn.visibility = View.GONE
        }else if(call=="Reward"){
            redeemBtn.setOnClickListener {

            if (currPoint <= requiredPoint.text.toString().toInt()) {
                redeemBtn.isEnabled = false
                redeemBtn.isClickable = false
                Toast.makeText(this, "You Don't have enough Point!", Toast.LENGTH_SHORT).show()
            } else {
                val newPoint = (currPoint - requiredPoint.text.toString().toInt())

                val currentTime = Calendar.getInstance().time
                ref.collection("Users").document(UID).update("Point", newPoint)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Successful Update Point!", Toast.LENGTH_SHORT).show()
                        val history = hashMapOf(
                            "Reward_ID" to prizeID,
                            "Name" to name.text.toString(),
                            "Point_Used" to requiredPoint.text.toString().toInt(),
                            "Redeem_Date" to currentTime.toString(),
                            "UID" to "sample1"

                        )
                        ref.collection("History_Reward")
                            .add(history)
                        this.finish()
                    }
            }
            }
        }
    }
}
