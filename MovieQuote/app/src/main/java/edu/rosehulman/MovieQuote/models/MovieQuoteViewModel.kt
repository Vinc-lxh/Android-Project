package edu.rosehulman.MovieQuote.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class MovieQuoteViewModel : ViewModel() {
    private var movieQuotes = ArrayList<MovieQuote>()
    var currentPos = 0

    fun getQuoteAt(pos:Int) = movieQuotes[pos]
    fun getCurrentQuote() = getQuoteAt(currentPos)
    fun addQuote(movieQuote: MovieQuote?) {
        val random = getRandom()
        val newQuote = movieQuote ?: MovieQuote("quote$random", "movie$random")
        //if movieQuote is not null, then use movieQuote, else create a new movieQuote
        movieQuotes.add(newQuote)
    }

    fun updateCurrentQuote(quote:String, movie: String){
        movieQuotes[currentPos].quote = quote
        movieQuotes[currentPos].moive = movie
    }
    fun removeCurrentQuote(){
        movieQuotes.removeAt(currentPos)
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