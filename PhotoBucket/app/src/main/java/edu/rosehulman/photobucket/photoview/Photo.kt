package edu.rosehulman.photobucket.photoview

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.ServerTimestamp

data class Photo(var caption: String = "", var URL: String = "", var isSelected: Boolean = false,var userID:String = "") {
    @get:Exclude
    var id = ""

    @ServerTimestamp
    var created: Timestamp? = null

    companion object{
        const val COLLECTION_PATH = "photo"
        const val CREATED_KEY = "created"

        fun from(snapshot: DocumentSnapshot): Photo{
            val mq = snapshot.toObject(Photo::class.java)!!
            mq.id = snapshot.id
            return mq
        }
    }
}