package edu.rosehulman.examtwolinx2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import edu.rosehulman.examtwolinx2.databinding.FragmentRemovedAppointmentsBinding
import edu.rosehulman.examtwolinx2.model.AppointmentViewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class RemovedAppointmentsFragment : Fragment() {

    private lateinit var binding: FragmentRemovedAppointmentsBinding
    private lateinit var model: AppointmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        model = ViewModelProvider(requireActivity()).get(AppointmentViewModel::class.java)
        binding = FragmentRemovedAppointmentsBinding.inflate(inflater, container, false)
        updateView()
        return binding.root

    }

    private fun updateView() {
        binding.deletedItem.text =model.getDeletedList()
    }

}