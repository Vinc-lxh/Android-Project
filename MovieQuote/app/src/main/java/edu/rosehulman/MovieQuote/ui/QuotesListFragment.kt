package edu.rosehulman.MovieQuote.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import edu.rosehulman.MovieQuote.adapters.MovieQuoteAdapter
import edu.rosehulman.MovieQuote.databinding.QuotesListFragmentBinding

class QuotesListFragment : Fragment() {



    private lateinit var binding:QuotesListFragmentBinding

    override fun onCreateView(    //fragment is onCreateView instead of onCreate
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = QuotesListFragmentBinding.inflate(inflater,container,false)
        //TODO:recycler
        val adapter = MovieQuoteAdapter(this)
        //set RecycleView and adapter properities
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        //Linear to GridLayoutManager
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.addItemDecoration(DividerItemDecoration(requireContext(),DividerItemDecoration.VERTICAL))
        binding.fab.setOnClickListener{
            adapter.addQuote(null)
        }


        return binding.root
    }

    companion object {
        fun newInstance() = QuotesListFragment()
        const val fragmentName = "QuotesListFragment"
    }


}