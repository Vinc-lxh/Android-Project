package edu.rosehulman.photobucket.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import edu.rosehulman.photobucket.adapers.PhotoViewAdapter
import edu.rosehulman.photobucket.databinding.FragmentPhotoListBinding


class PhotoListFragment : Fragment() {
    private lateinit var binding: FragmentPhotoListBinding
    private lateinit var adapter: PhotoViewAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPhotoListBinding.inflate(inflater, container, false)

        //TODO: make recycler
        adapter = PhotoViewAdapter(this) //pass the fragment itself as parameter
        //set recyclerview and adapter properties
        binding.recyclerView.adapter = adapter //recyclerView has an adapter
        adapter.addListener(fragmentName)
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(),3)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.addItemDecoration(DividerItemDecoration(requireContext(),DividerItemDecoration.HORIZONTAL))
        binding.fab.setOnClickListener{
            adapter.addPhoto(null)
            Log.d("PV","Here")
        }

        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter.removeListener(fragmentName)
    }
    companion object{
        const val fragmentName = "PhotoListFragment"
    }

}