package edu.rosehulman.examtwolinx2.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.color.MaterialColors
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import edu.rosehulman.examtwolinx2.AppointmentsFragment
import edu.rosehulman.examtwolinx2.R
import edu.rosehulman.examtwolinx2.model.Appointment
import edu.rosehulman.examtwolinx2.model.AppointmentViewModel

class AppointmentAdapter (val fragment: AppointmentsFragment): RecyclerView.Adapter<AppointmentAdapter.AppointmentViewHolder>() {

    val model = ViewModelProvider(fragment.requireActivity()).get(AppointmentViewModel::class.java)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentViewHolder {
        val view  = LayoutInflater.from(parent.context).inflate(R.layout.appointment_layout,parent,false)
        return AppointmentViewHolder(view)
    }

    override fun onBindViewHolder(holder: AppointmentViewHolder, position: Int) {
        holder.bind(model.getAppoitmentAt(position),position)
    }

    override fun getItemCount() = model.size()

    fun addAppointment(appm: Appointment?) {
        model.addAppointment(appm)
        notifyDataSetChanged()
    }

    fun deleteSelected() {
         model.removeCurrentSelected()
        //model.addAppointment(null)
        Log.d("appM","deleted")
        notifyDataSetChanged()
    }

    fun undoRemove() {
        model.undoRemove()
        notifyDataSetChanged()
    }


    inner class AppointmentViewHolder(itemview: View): RecyclerView.ViewHolder(itemview){
        val appointerTextView: TextView = itemView.findViewById<TextView>(R.id.appointer)
        val timeTextView: TextView = itemView.findViewById<TextView>(R.id.time)
        val clockIcon: ImageView = itemView.findViewById<ImageView>(R.id.clock)

        init {
            itemView.setOnClickListener{
                model.updatePos(adapterPosition)
                model.toggleCurrentAppM()
                notifyDataSetChanged()
//                fragment.findNavController().navigate(R.id.navigation_quote_edit)
            }
            itemView.setOnLongClickListener{
                model.updatePos(adapterPosition)
                model.toggleCurrentAppM()
                notifyDataSetChanged()
                true
            }
        }

        fun bind(appm: Appointment, position: Int){
            appointerTextView.text = appm.name
            timeTextView.text = appm.time
            clockIcon.setOnClickListener{
                val picker =
                    MaterialTimePicker.Builder()
                        .setTimeFormat(TimeFormat.CLOCK_24H)
                        .setHour(model.getAppoitmentAt(adapterPosition).htime)
                        .setMinute(model.getAppoitmentAt(adapterPosition).mtime)
                        .setTitleText("Select Appointment Time")
                        .build()
                picker.show(fragment.parentFragmentManager, "tag");

                picker.addOnPositiveButtonClickListener{
                    val time  = String.format("%d:%2d", picker.hour,picker.minute)
                    model.currentPos = adapterPosition
                    model.updateCurrentAppointment(time,picker.hour,picker.minute)
                    notifyDataSetChanged()
                }

            }
            itemView.setBackgroundColor(
                if(appm.isSelected){
                    //Color.GREEN
                    MaterialColors.getColor(
                        fragment.requireContext(),
                        R.attr.colorAccent,
                        Color.WHITE
                    )
                }else{
                    Log.d("appT","p is $position")
                    if((position+1)%2==0){
                        Color.GRAY
                    }else{
                        Color.YELLOW
                    }
                }
            )
        }
    }

}