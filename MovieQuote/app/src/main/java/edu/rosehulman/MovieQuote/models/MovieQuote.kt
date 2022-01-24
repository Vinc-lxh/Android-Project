package edu.rosehulman.MovieQuote.models

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.ServerTimestamp

data class MovieQuote(var quote: String = "",
                      var moive: String = "",
                      var isSelected: Boolean = false){
    @get:Exclude
    var id = ""

    @ServerTimestamp
    var created: Timestamp? = null



    override fun toString(): String {
        return if(quote.isNotBlank()) "$quote, from $moive" else ""
    }
    companion object{
        const val COLLECTION_PATH = "quotes"
        const val CREATED_KEY = "created"
        fun from(snapshot: DocumentSnapshot): MovieQuote{
            val mq = snapshot.toObject(MovieQuote::class.java)!!
            mq.id = snapshot.id
            return mq
        }
    }
}
