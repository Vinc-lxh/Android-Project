package edu.rosehulman.MovieQuote.models

data class MovieQuote(var quote: String = "", var moive: String = ""){
    override fun toString(): String {
        return if(quote.isNotBlank()) "$quote, from $moive" else ""
    }
}
