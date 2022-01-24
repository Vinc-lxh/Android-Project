package edu.rosehulman.examtwolinx2.model

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class AppointmentViewModel:ViewModel() {
    private var appointments = ArrayList<Appointment>()
    private var deleted = ArrayList<Appointment>()

    var currentPos = 0

    fun getAppoitmentAt(pos:Int) = appointments[pos]
    fun getCurrentAppm() = getAppoitmentAt(currentPos)

    fun addAppointment(appm: Appointment?) {
        val name = nameOptions[Random.nextInt(nameOptions.size)]
        val htime = (0..23).random()
        val mtime = (0..59).random()
        val newAppm = appm ?: Appointment("$name", "$htime:$mtime",htime,mtime)
        appointments.add(newAppm)
        sortByTime()
    }

    fun updateCurrentAppointment(time: String,hh:Int,mm:Int){
        appointments[currentPos].time = time
        appointments[currentPos].htime = hh
        appointments[currentPos].mtime = mm
        sortByTime()
    }
    fun removeCurrentSelected(){
        val appIterator = appointments.iterator()
        while(appIterator.hasNext()){
            val nextApp = appIterator.next()
            if(nextApp.isSelected){
                nextApp.isSelected = false
                deleted.add(nextApp)
                appIterator.remove()
            }
        }
        currentPos = 0
    }

    fun updatePos(pos: Int){
        currentPos = pos
    }
    fun getDeletedList(): String {
        var result = ""
        for(d in deleted){
            result+=d.toString() + "\n"
        }
        return result
    }

    fun sortByTime(){
        appointments.sortWith(compareBy<Appointment>{it.htime}.thenBy { it.mtime })
    }

    fun size() = appointments.size
    fun toggleCurrentAppM() {
        appointments[currentPos].isSelected = !appointments[currentPos].isSelected
    }

    fun undoRemove() {
        appointments.addAll(deleted)
        deleted.clear()
        sortByTime()
        currentPos = 0
    }

    private val nameOptions = arrayOf(
        "Evan",
        "Stephen",
        "Charlie",
        "Natalie",
        "Michael",
        "Jasmine",
        "Xingheng",
        "Elvis",
        "Nick",
        "Adithya",
        "Jasmine",
        "Nicholas",
        "Blake",
        "Elle",
        "Rachel",
        "Darren",
        "Deng",
        "Vik",
        "Max",
        "Vuk",
        "Brandon",
        "Edward",
        "Lyra",
        "Jack",
        "Hannah", // plus some other very-common names.
        "Emily",
        "Sarah",
        "Madison",
        "Brianna",
        "Kaylee",
        "Kaitlyn",
        "Michael",
        "Jacob",
        "Matthew",
        "Nicholas",
        "Christopher",
        "Joseph",
        "Zachary",
        "Anna",
        "Cornelius"
    )

}