package edu.rosehulman.MovieQuote.models

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.rosehulman.MovieQuote.Constants
import kotlin.random.Random

class MovieQuoteViewModel : ViewModel() {
    var movieQuotes = ArrayList<MovieQuote>()
    var currentPos = 0

    fun getQuoteAt(pos:Int) = movieQuotes[pos]
    fun getCurrentQuote() = getQuoteAt(currentPos)

    val ref = Firebase.firestore.collection(MovieQuote.COLLECTION_PATH)
    val subscriptions = HashMap<String,ListenerRegistration>()
    fun addListener(observer: () -> Unit) {
        val subscription = ref
            .orderBy(MovieQuote.CREATED_KEY, Query.Direction.ASCENDING)
            .addSnapshotListener{snapshot:QuerySnapshot?,error:FirebaseFirestoreException?->
            error?.let{
                Log.d(Constants.TAG,"Error: $error")
                return@addSnapshotListener
            }
            movieQuotes.clear()
            snapshot?.documents?.forEach {
                movieQuotes.add(MovieQuote.from(it))
            }
            observer()
        }

        //subscriptions[] = subscription
    }

//    fun removeListener(){
//        subscription.remove()
//    }
    fun addQuote(movieQuote: MovieQuote?) {
        val random = getRandom()
        val newQuote = movieQuote ?: MovieQuote("quote$random", "movie$random")

        ref.add(newQuote)
//        movieQuotes.add(newQuote)
    }

    fun updateCurrentQuote(quote:String, movie: String){
        movieQuotes[currentPos].quote = quote
        movieQuotes[currentPos].moive = movie
        ref.document(getCurrentQuote().id).set(getCurrentQuote())
        //.update can update a field
    }
    fun removeCurrentQuote(){
        ref.document(getCurrentQuote().id).delete()
        currentPos = 0
    }
    fun updatePos(pos: Int){
        currentPos = pos
    }

    fun size() = movieQuotes.size

    fun getRandom() = Random.nextInt(100)
    fun toggleCurrentQuote() {
        movieQuotes[currentPos].isSelected = !movieQuotes[currentPos].isSelected
    }
}