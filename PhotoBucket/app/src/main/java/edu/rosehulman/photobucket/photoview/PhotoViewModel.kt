package edu.rosehulman.photobucket.photoview


import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.random.Random

class PhotoViewModel:ViewModel() {
    private var photos = ArrayList<Photo>()
    var currentPos = 0
    val ref = Firebase.firestore.collection(PhotoViewModel.COLLECTION_PATH)
    fun addListener() {
        val subscription = ref
            .addSnapshotListener{ snapshot: QuerySnapshot?, error: FirebaseFirestoreException?->
                error?.let{
                    Log.d(Constants.TAG,"Error: $error")
                    return@addSnapshotListener
                }
                photos.clear()
                snapshot?.documents?.forEach {
                    photos.add(Photo.from(it))
                }
            }
    }
    fun getPhotoAt(position: Int):Photo{
        return photos[position]
    }

    fun getCurrentPhoto() = getPhotoAt(currentPos)

    fun addPhoto(photo: Photo?){
        val p = photo ?: Photo(useGivenOrRandomCaption(""),useGivenOrRandom(""))
//        photos.add(p)
        ref.add(p)
    }

    fun updateCurrentPhoto(cap:String, url: String){
        photos[currentPos].caption = useGivenOrRandomCaption(cap)
        photos[currentPos].URL = useGivenOrRandom(url)
    }

    fun removeCurrentPhoto(){
        photos.removeAt(currentPos)
        currentPos = 0
    }

    fun updatePos(pos: Int){
        currentPos = pos
    }

    fun toggleCurrentPhoto() {
        photos[currentPos].isSelected = !photos[currentPos].isSelected
    }

    fun size() = photos.size

    fun useGivenOrRandom(given: String): String {
        if (given.isNotBlank()) {
            return given
        }
        val idx = Random.nextInt(urls.size)
        return urls[idx]
    }

    fun useGivenOrRandomCaption(given: String): String {
        if (given.isNotBlank()) {
            return given
        }
        val idx = Random.nextInt(captions.size)
        return captions[idx]
    }



    companion object {
        val COLLECTION_PATH = "photo"
        val captions = arrayListOf(
            "asfasffasdf",
            "98ashdfasdf",
            "asdfnlajfo23",
            "ojoijoasdf",
            "238ahfaosdfjo2i3r",

        )


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