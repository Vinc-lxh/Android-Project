package edu.rosehulman.HelloButton

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import edu.rosehulman.HelloButton.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding//?
    var nClicks = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) //R for resource

        binding = ActivityMainBinding.inflate(layoutInflater) //??
        setContentView(binding.root)

        binding.button.setOnClickListener{
            nClicks++
            updateView()
        }
    }

    fun updateView(){
        val s = "You clicked the button $nClicks times" //val not mutable
        binding.messageTextView.text = s
        if(nClicks>10){
            binding.messageTextView.setTextColor(getColor(R.color.black))
            binding.messageTextView.visibility = View.INVISIBLE
        }
    }
}