package edu.rosehulman.photobucket.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import edu.rosehulman.photobucket.R
import edu.rosehulman.photobucket.databinding.FragmentPhotosEditBinding
import edu.rosehulman.photobucket.photoview.Photo
import edu.rosehulman.photobucket.photoview.PhotoViewModel

class PhotoEditFragment : Fragment() {
    private lateinit var model: PhotoViewModel
    private lateinit var  binding: FragmentPhotosEditBinding

    // This property is only valid between onCreateView and
    // onDestroyView.


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       model = ViewModelProvider(requireActivity()).get(PhotoViewModel::class.java)

        binding = FragmentPhotosEditBinding.inflate(inflater, container, false)
        setupButtons()
        updateView()
        return binding.root
    }

    fun View.hideKeyBoard(){
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    private fun setupButtons() {
        binding.showButton.setOnClickListener {
            val url = binding.URL.text.toString()
            val caption = binding.caption.text.toString()
            model.updateCurrentPhoto(caption,url)

            //model.photo = Photo(caption,url)
            Log.d("photo3","$url and $caption")
            updateView()
            it.hideKeyBoard()
            //navigate to deatil page
            findNavController().navigate(R.id.nav_photos_detail)
        }

        binding.removeButton.setOnClickListener {
            model.removeCurrentPhoto()
            findNavController().navigate(R.id.nav_photos_list)
        }
    }

    private fun updateView() {
        binding.caption.setText(model.getCurrentPhoto().caption.toString())
        binding.URL.setText(model.getCurrentPhoto().URL.toString())
    }


}