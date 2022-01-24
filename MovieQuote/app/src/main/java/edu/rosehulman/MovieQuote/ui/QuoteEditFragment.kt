package edu.rosehulman.MovieQuote.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
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
            //findNavController().navigate(R.id.navigation_quotes_detail)
            //TODO: clear button
            val picker =
                MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_12H)
                    .setHour(12)
                    .setMinute(10)
                    .setTitleText("Select Quote time")
                    .build()
            picker.addOnPositiveButtonClickListener{
                val s  = String.format("Time: %d:%2d", picker.hour,picker.minute)
                //Toast.makeText(requireContext(),s,Toast.LENGTH_LONG).show()
                Snackbar.make(requireView(),s,Snackbar.LENGTH_INDEFINITE)
                    .setAction(getString(R.string.continueButton)){
                        findNavController().navigate(R.id.navigation_quotes_detail)
                    }
                    .setAnchorView(requireActivity().findViewById(R.id.nav_view))
                    .show()
                //consider: navigating away from fragment is troublesome
            }

            picker.show(parentFragmentManager, "tag");
        }
        binding.clearButton.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Are you sure?")
                .setMessage("Are you sure you want to delete this quote?")
                .setPositiveButton(android.R.string.ok){ dialog,which->
                    binding.quoteTextView.text.clear()
                    binding.movieTextView.setText("")
                    model.updateCurrentQuote("","")
                    updateView()
                }
                .setNegativeButton(android.R.string.cancel,null)
                .show()





        }
    }

    private fun updateView() {
        binding.currentQuoteTextView.text = model.getCurrentQuote().toString()

    }

}