package edu.rosehulman.MovieQuote.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import edu.rosehulman.MovieQuote.R
import edu.rosehulman.MovieQuote.databinding.FragmentQuoteDetailBinding
import edu.rosehulman.MovieQuote.models.MovieQuoteViewModel


class QuoteDetailFragment : Fragment() {
    private lateinit var binding: FragmentQuoteDetailBinding
    private lateinit var model: MovieQuoteViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentQuoteDetailBinding.inflate(inflater,container,false)
        model = ViewModelProvider(requireActivity()).get(MovieQuoteViewModel::class.java)
        updateView()
        return binding.root
    }

    private fun updateView() {
        Log.d("MQ", "model ${model.movieQuote} in detail update view")
        binding.movieTextView.text = model.movieQuote.moive
        binding.quoteTextView.text = model.movieQuote.quote
    }

}