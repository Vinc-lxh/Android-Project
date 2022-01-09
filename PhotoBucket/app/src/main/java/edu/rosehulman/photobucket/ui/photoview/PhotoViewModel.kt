package edu.rosehulman.photobucket.ui.photoview


import androidx.lifecycle.ViewModel
import kotlin.random.Random



class PhotoViewModel:ViewModel() {

    var photo = Photo()
    fun getCaption() = photo.caption
    fun getURL() = useGivenOrRandom(photo.URL)


    fun useGivenOrRandom(given: String): String {
        if (given.isNotBlank()) {
            return given
        }
        val idx = Random.nextInt(urls.size)
        return urls[idx]
    }

    companion object {
        val urls = arrayListOf(
            "https://cdn.britannica.com/91/181391-050-1DA18304/cat-toes-paw-number-paws-tiger-tabby.jpg",
            "https://upload.wikimedia.org/wikipedia/commons/b/b1/VAN_CAT.png",
            "https://cdn.britannica.com/68/160068-050-53FE2889/Snowshoe-cat.jpg",    
//
//            "https://commons.wikimedia.org/wiki/Category:Architecture_by_city#/media/File:Ray_and_Maria_Stata_Center_(MIT).JPG",
//            "https://commons.wikimedia.org/wiki/Category:Stained-glass_windows_in_Bratislava#/media/File:Bratislavastainedglass.JPG",
//            "https://commons.wikimedia.org/wiki/Category:Stained-glass_windows_in_Bratislava#/media/File:COA_Palugyay_Ferenc.jpg",       "https://commons.wikimedia.org/wiki/Category:Stained-glass_windows_in_Bratislava#/media/File:Frantiskansky_kostol37.jpg",
//            "https://commons.wikimedia.org/wiki/Category:Stained-glass_windows_in_Athens#/media/File:St._Dionysios_Catholic_-_Vitrail,_2005.jpg",
//
    //
            )
    }


}