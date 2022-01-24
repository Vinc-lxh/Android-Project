package edu.rosehulman.examtwolinx2

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import edu.rosehulman.examtwolinx2.adapter.AppointmentAdapter
import edu.rosehulman.examtwolinx2.databinding.FragmentAppointmentBinding
import edu.rosehulman.examtwolinx2.model.AppointmentViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class AppointmentsFragment : Fragment() {
    private lateinit var model: AppointmentViewModel
    private lateinit var binding: FragmentAppointmentBinding
    private lateinit var adapter:AppointmentAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        model = ViewModelProvider(requireActivity()).get(AppointmentViewModel::class.java)
        binding = FragmentAppointmentBinding.inflate(inflater, container, false)
        adapter = AppointmentAdapter(this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(requireContext(),
                DividerItemDecoration.HORIZONTAL)
        )
        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_settings -> {
            // User chose the "Settings" item, show the app settings UI...
            true
        }

        R.id.delete_appointment -> {
            // User chose the "Favorite" action, mark the current item
            adapter.deleteSelected()
            Snackbar.make(requireView(),"Deleting", Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(R.string.undo_string)){
                    adapter.undoRemove()
                }
                .show()
            true
        }

        R.id.shortcut -> {
            findNavController().navigate(R.id.RemovedAppointmentsFragment)
            true
        }

        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }
    override fun onResume() {
        super.onResume()
        val fab = (requireActivity() as MainActivity).findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            adapter.addAppointment(null)
            Log.d("appm","added app")
        }
    }

}