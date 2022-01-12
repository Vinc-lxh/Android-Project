package edu.rosehulman.photobucket.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.ViewModelProvider
import coil.load
import edu.rosehulman.photobucket.R
import edu.rosehulman.photobucket.databinding.FragmentPhotoDetailBinding
import edu.rosehulman.photobucket.photoview.PhotoViewModel

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
        binding.caption.text = model.getCurrentPhoto().caption
        binding.urlImage.load(model.getCurrentPhoto().URL) {
            crossfade(true)
            //transformations(CircleCropTransformation())
        }
        if(model.getCurrentPhoto().isSelected){
            binding.detailIcon.setImageResource(R.drawable.ic_baseline_favorite_24)
        }else{
            binding.detailIcon.setImageResource(R.drawable.ic_baseline_image_24)
        }

    }


}