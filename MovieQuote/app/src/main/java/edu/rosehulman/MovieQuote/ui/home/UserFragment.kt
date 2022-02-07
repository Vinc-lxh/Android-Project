package edu.rosehulman.MovieQuote.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import coil.load
import coil.transform.CircleCropTransformation
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import edu.rosehulman.MovieQuote.R

import edu.rosehulman.MovieQuote.databinding.FragmentUserBinding
import edu.rosehulman.MovieQuote.models.User
import edu.rosehulman.MovieQuote.models.UserViewModel

class UserFragment : Fragment() {

    private lateinit  var binding: FragmentUserBinding

    // This property is only valid between onCreateView and
    // onDestroyView.


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserBinding.inflate(inflater,container,false)
        binding.logout.setOnClickListener {
            Firebase.auth.signOut()
        }
        binding.edit.setOnClickListener{
            findNavController().navigate(R.id.navigation_user_edit)
        }
        val userModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)
        with(userModel.user!!){
            binding.age.text = "age $age"
            binding.major.text = "major $major"
            binding.profileName.text = name
            if(storageUriString.isNotEmpty()){
                binding.iconImage.load(storageUriString) {
                    crossfade(true)
                    transformations(CircleCropTransformation())
                }
            }
        }


        return binding.root
    }



}