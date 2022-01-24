package edu.rosehulman.examtwolinx2.model

data class Appointment (var name: String = "", var time: String = "",var htime: Int = 0,var mtime:Int = 0, var isSelected: Boolean = false){
    override fun toString(): String {
        return if(name.isNotBlank()) "$name,  $time" else ""
    }

}