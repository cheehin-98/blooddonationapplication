/*
package com.example.blooddonationapplication.ui.util

import com.example.blooddonationapplication.ui.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import io.grpc.ClientStreamTracer
import java.lang.NullPointerException

object FirestoreUtil  {
    private val firestoreInstance: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

    private val currentUserDocRef: DocumentReference
    get() = firestoreInstance.document("Users/${FirebaseAuth.getInstance().uid
        ?:throw NullPointerException("UID is null.")}")

    fun initCurrentUserIfFirstTime(onComplete: () -> Unit){
        currentUserDocRef.get().addOnSuccessListener { documentSnapshot ->
            if (!documentSnapshot.exists()){
                val newUser = User(FirebaseAuth.getInstance().currentUser?.displayName ?: "",
                "", "",null)
                currentUserDocRef.set(newUser).addOnSuccessListener {
                    onComplete()
                }
            }
            else onComplete()

        }
    }
        fun updateCurrentUser(name:String = "", email:String = "", userType:String = "", profileRef:String?= null){
            val userFieldMap  = mutableMapOf<String, Any>()
            if (name.isNotBlank()) userFieldMap["Name"] = name
            if (email.isNotBlank()) userFieldMap["Email"] = email
            if (userType.isNotBlank()) userFieldMap["Type"] = userType
            if (profileRef !=null)
                userFieldMap["profile.jpg"] = profileRef
            currentUserDocRef.update(userFieldMap)
        }
    fun getCurrentUser(onComplete: (User) -> Unit){
        currentUserDocRef.get()
            .addOnSuccessListener {
                onComplete(it.toObject(User::class.java)
            }
    }

}*/
