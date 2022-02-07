package edu.rosehulman.photobucket.photoview


import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.random.Random

class PhotoViewModel:ViewModel() {
    private var photos = ArrayList<Photo>()
    var currentPos = 0
    val ref = Firebase.firestore.collection(Photo.COLLECTION_PATH)
    var OwnPhoto = true
    val subscriptions = HashMap<String,ListenerRegistration>()
    val settintRef = Firebase.firestore.collection("settings").document("settings")
    var settingString = "Photo"
    fun addSettingListener(fragmentName: String, observer: () -> Unit){
        Log.d(Constants.TAG,"Adding listener for $fragmentName")
        val subscription = settintRef
            .addSnapshotListener{ snapshot, error: FirebaseFirestoreException?->
                error?.let{
                    Log.d(Constants.TAG,"Error: $error")
                    return@addSnapshotListener
                }
                settingString = snapshot?.get("title").toString()
                Log.d(Constants.TAG,"setting title is for $settingString")
                observer()
            }
        subscriptions[fragmentName] = subscription
    }

    fun addListener(fragmentName: String, observer: () -> Unit) {
        val uid = Firebase.auth.currentUser?.uid
        Log.d(Constants.TAG,"Adding listener for $fragmentName")
        val subscription = ref
            .orderBy(Photo.CREATED_KEY,Query.Direction.ASCENDING)
            .whereEqualTo("userID",uid)
            .addSnapshotListener{ snapshot: QuerySnapshot?, error: FirebaseFirestoreException?->
                error?.let{
                    Log.d(Constants.TAG,"Error: $error")
                    return@addSnapshotListener
                }
                photos.clear()
                snapshot?.documents?.forEach {
                    photos.add(Photo.from(it))
                }
                observer()
            }
        subscriptions[fragmentName] = subscription
    }
    fun addAllListener(fragmentName: String, observer: () -> Unit) {
        Log.d(Constants.TAG,"Adding listener for $fragmentName")
        val subscription = ref
            .orderBy(Photo.CREATED_KEY,Query.Direction.ASCENDING)
            .addSnapshotListener{ snapshot: QuerySnapshot?, error: FirebaseFirestoreException?->
                error?.let{
                    Log.d(Constants.TAG,"Error: $error")
                    return@addSnapshotListener
                }
                photos.clear()
                snapshot?.documents?.forEach {
                    photos.add(Photo.from(it))
                }
                observer()
            }
        subscriptions[fragmentName] = subscription
    }

    fun removeListener(fragmentName: String) {
        Log.d(Constants.TAG,"removing listener for $fragmentName")
        subscriptions[fragmentName]?.remove()// this tell firebase to stop listening
        subscriptions.remove(fragmentName)
    }

    fun getPhotoAt(position: Int):Photo{
        return photos[position]
    }

    fun toggleOwnPhoto(){
        Log.d(Constants.TAG,"toggled Own photo")
        OwnPhoto = !OwnPhoto
    }
    fun checkOwnPhoto(): Boolean {
        val uid = Firebase.auth.currentUser!!.uid
        return (getCurrentPhoto().userID.equals(uid))
    }
    fun getCurrentPhoto() = getPhotoAt(currentPos)

    fun addPhoto(photo: Photo?){
        val uid = Firebase.auth.currentUser!!.uid
        val p = photo ?: Photo(useGivenOrRandomCaption(""),useGivenOrRandom(""), userID = uid)
//        photos.add(p)
        ref.add(p)
    }

    fun updateCurrentPhoto(cap:String, url: String){
        photos[currentPos].caption = useGivenOrRandomCaption(cap)
        photos[currentPos].URL = useGivenOrRandom(url)
        ref.document(getCurrentPhoto().id).set(getCurrentPhoto())
    }

    fun removeCurrentPhoto(){
        ref.document(getCurrentPhoto().id).delete()
        currentPos = 0
    }

    fun updatePos(pos: Int){
        currentPos = pos
    }

    fun toggleCurrentPhoto() {
        photos[currentPos].isSelected = !photos[currentPos].isSelected
        ref.document(getCurrentPhoto().id).update("selected",true)
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