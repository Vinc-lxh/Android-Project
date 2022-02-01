package edu.rosehulman.MovieQuote.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

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
        val userModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)
        binding.age.text = userModel.user?.age.toString()
        binding.major.text = userModel.user?.major
        binding.profileName.text = userModel.user?.name

        return binding.root
    }



}