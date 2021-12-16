package edu.rosehulman.MovieQuote.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MovieQuoteViewModel : ViewModel() {
    var movieQuote = MovieQuote()
}