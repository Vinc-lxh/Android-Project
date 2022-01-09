package edu.rosehulman.photobucket.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import coil.load
import coil.transform.CircleCropTransformation
import edu.rosehulman.photobucket.databinding.FragmentPhotoDetailBinding
import edu.rosehulman.photobucket.ui.photoview.PhotoViewModel

class PhotoDetailFragment : Fragment() {
    private lateinit var binding:FragmentPhotoDetailBinding
    private lateinit var model:PhotoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentPhotoDetailBinding.inflate(inflater,container,false)
        model = ViewModelProvider(requireActivity()).get(PhotoViewModel::class.java)
        updateView()
        return binding.root
    }

    private fun updateView() {
        Log.d("photo4","${model.getURL()}")
        binding.caption.text = model.getCaption()
        binding.urlImage.load(model.getURL()) {
            crossfade(true)
            //transformations(CircleCropTransformation())
        }

    }


}