package edu.rosehulman.photobucket.ui.Profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import edu.rosehulman.photobucket.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private lateinit var galleryViewModel: ProfileViewModel
    private lateinit var binding: FragmentProfileBinding

    // This property is only valid between onCreateView and
    // onDestroyView.


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        galleryViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        binding = FragmentProfileBinding.inflate(inflater, container, false)
        binding.logoutButton.setOnClickListener{
            Firebase.auth.signOut()

        }
        binding.imageView.load("https://lh3.googleusercontent.com/pw/AM-JKLUva3DS_Y7qPgHJBEuL_sbOPJ3l09NLBcNwgDu5vSDHdYX8ByuhrAGx2UpgUStjEgKuScVnBnFCNsJLzuZyGoevJjTb6bKcb8TxvRS-6wkIT6TmcOwCB1JVk4YZ-Vri2tLo4aGPxy7f0yrI9wx8mV8z=w1178-h1202-no") {
            crossfade(true)
            //transformations(CircleCropTransformation())
        }


        return binding.root
    }


}