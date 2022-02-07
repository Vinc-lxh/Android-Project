package edu.rosehulman.photobucket.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import edu.rosehulman.photobucket.R
import edu.rosehulman.photobucket.adapers.PhotoViewAdapter
import edu.rosehulman.photobucket.databinding.FragmentPhotoListBinding
import edu.rosehulman.photobucket.photoview.Constants
import edu.rosehulman.photobucket.photoview.PhotoViewModel


class PhotoListFragment : Fragment() {
    private lateinit var binding: FragmentPhotoListBinding
    private lateinit var adapter: PhotoViewAdapter
    private lateinit var model: PhotoViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPhotoListBinding.inflate(inflater, container, false)

        //TODO: make recycler
        adapter = PhotoViewAdapter(this) //pass the fragment itself as parameter
        //set recyclerview and adapter properties
        binding.recyclerView.adapter = adapter //recyclerView has an adapter
        //adapter.addListener(fragmentName)

        model = ViewModelProvider(requireActivity()).get(PhotoViewModel::class.java)
        if(model.OwnPhoto){
            adapter.addListener(fragmentName)
        }else{
            adapter.addAllListener(fragmentName)
        }
        model.addSettingListener(fragmentName){
            setActivityTitle(model.settingString)
        }
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(),3)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.addItemDecoration(DividerItemDecoration(requireContext(),DividerItemDecoration.HORIZONTAL))
        binding.fab.setOnClickListener{
            adapter.addPhoto(null)
            Log.d("PV","Here")
        }
        setHasOptionsMenu(true)
        return binding.root

    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.switch_phto -> {
            model.toggleOwnPhoto()
            adapter.removeListener(fragmentName)
                if(model.OwnPhoto){
                    adapter.addListener(fragmentName)
                }else{
                    adapter.addAllListener(fragmentName)
                }
            true
        }else -> {
            super.onOptionsItemSelected(item)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        adapter.removeListener(fragmentName)
    }
    companion object{
        const val fragmentName = "PhotoListFragment"
    }

    fun Fragment.setActivityTitle(xxx: String) {
        (activity as AppCompatActivity?)?.supportActionBar?.title = xxx
    }
}