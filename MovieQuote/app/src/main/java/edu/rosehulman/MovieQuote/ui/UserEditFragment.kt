package edu.rosehulman.MovieQuote.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import edu.rosehulman.MovieQuote.Constants
import edu.rosehulman.MovieQuote.R
import edu.rosehulman.MovieQuote.databinding.FragmentUserEditBinding
import edu.rosehulman.MovieQuote.models.UserViewModel

class UserEditFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        val userModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)
        Log.d(Constants.TAG, "User in edit fragment: ${userModel.user}")

        val binding = FragmentUserEditBinding.inflate(inflater, container, false)
        binding.userEditDoneButton.setOnClickListener {
            // Save user info into Firestore.
            val newAgeString = binding.userEditAgeEditText.text.toString()
            userModel.update(
                newName=binding.userEditNameEditText.text.toString(),
                newAge=if (newAgeString.isNotBlank()) newAgeString.toInt() else -1,
                newMajor=binding.userEditMajorEditText.text.toString(),
                newHasCompletedSetup=true
            )
            findNavController().navigate(R.id.navigation_home)
        }

        userModel.getOrMakeUser {
            with(userModel.user!!) {
                Log.d(Constants.TAG, "$this")
                binding.userEditNameEditText.setText(name)
                binding.userEditAgeEditText.setText(age.toString())
                binding.userEditMajorEditText.setText(major)
            }
        }
        return binding.root
    }

}
