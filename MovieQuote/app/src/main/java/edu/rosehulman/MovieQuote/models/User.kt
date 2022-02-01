package edu.rosehulman.MovieQuote.models

data class User(
    var name : String = "",
    var age: Int = -1,
    var major: String = "unkown",
    var hasCompletedSetup: Boolean = false
) {




    companion object{
        const val COLLECTION_PATH = "users"
    }
}