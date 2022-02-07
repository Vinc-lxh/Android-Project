package edu.rosehulman.photobucket.ui.slideshow

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.rosehulman.photobucket.R
import edu.rosehulman.photobucket.databinding.FragmentSettingBinding
import edu.rosehulman.photobucket.photoview.Constants
import edu.rosehulman.photobucket.photoview.PhotoViewModel

class SettingFragment : Fragment() {

    private lateinit var model: PhotoViewModel
    private  lateinit var binding: FragmentSettingBinding
    private lateinit var settintRef: DocumentReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        model = ViewModelProvider(this).get(PhotoViewModel::class.java)

        binding = FragmentSettingBinding.inflate(inflater, container, false)
        settintRef = Firebase.firestore.collection("settings").document("settings")
        model.addSettingListener(fragmentName ){
            updateView()
        }
        setupButton()
        updateView()
        return binding.root
    }
    companion object{
        const val fragmentName = "SettingFragment"
    }
    private fun updateView() {
        val text = settintRef.get() .addOnSuccessListener { document ->
            if (document != null) {
//                val currentTitle = document.data?.get("title").toString()
                binding.appbarName.setText(model.settingString)
            }
        }
    }

    private fun setupButton() {
        binding.doneButton.setOnClickListener {
            val targetTitle = binding.appbarName.text.toString()
            Log.d(Constants.TAG,"current Title is $targetTitle")
            val city = hashMapOf(
                "title" to targetTitle)
            settintRef.set(city)
            findNavController().navigate(R.id.nav_photos_list)

        }
    }


}