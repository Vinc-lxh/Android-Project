package edu.rosehulman.MovieQuote.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import edu.rosehulman.MovieQuote.R
import edu.rosehulman.MovieQuote.databinding.FragmentQuoteEditBinding
import edu.rosehulman.MovieQuote.models.MovieQuote
import edu.rosehulman.MovieQuote.models.MovieQuoteViewModel


class QuoteEditFragment : Fragment() {

    private lateinit var model: MovieQuoteViewModel
    private lateinit var binding: FragmentQuoteEditBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        model = ViewModelProvider(requireActivity()).get(MovieQuoteViewModel::class.java)
            //requireActivity() instead of this
        binding = FragmentQuoteEditBinding.inflate(inflater, container, false)
        setupButtons()
        updateView()
        return binding.root
    }

    private fun setupButtons() {

        binding.doneButton.setOnClickListener{
            val q = binding.quoteTextView.text.toString()
            val m = binding.movieTextView.text.toString()
//            model.movieQuote = MovieQuote(q,m)
            model.updateCurrentQuote(q,m)

            updateView()
            findNavController().navigate(R.id.navigation_quotes_detail)
            //TODO: clear button
        }
        binding.clearButton.setOnClickListener {
            binding.quoteTextView.text.clear()
            binding.movieTextView.setText("")
            model.updateCurrentQuote("","")

            updateView()

        }
    }

    private fun updateView() {
        binding.currentQuoteTextView.text = model.getCurrentQuote().toString()

    }

}