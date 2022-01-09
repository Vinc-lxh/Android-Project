package edu.rosehulman.ExamOneLinx2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import edu.rosehulman.ExamOneLinx2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var ArrowButtons: Array<ImageButton>
    private lateinit var penButton: Button
    private lateinit var resetButton: Button
    private lateinit var game: EtchASketch
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ArrowButtons = Array(4){ImageButton(this)} //up down left right
        penButton = Button(this)
        resetButton = Button(this)
        game = EtchASketch(this)
        setupButtons()
        updateView()
    }

    private fun setupButtons() {
        for(i in 0 until 4){

            val id  = when(i){
                0 -> resources.getIdentifier("arrow_up","id",packageName)
                1 -> resources.getIdentifier("arrow_down","id",packageName)
                2 -> resources.getIdentifier("arrow_left","id",packageName)
                3 -> resources.getIdentifier("arrow_right","id",packageName)
                else -> {resources.getIdentifier("arrow_up","id",packageName)}
            }
            ArrowButtons[i] = binding.root.findViewById(id)
            ArrowButtons[i].setOnClickListener {
                Log.d("game","Up is pressed")
                game.pressButtonAt(i)
                updateView()
            }
        }
        binding.reset.setOnClickListener {
            game.reset()
            Log.d("game","gamereset")
            updateView()
        }
        binding.pen.setOnClickListener{
            game.drawing()
            Log.d("game","Toggle Drawing")
            updateView()
        }

    }

    private fun updateView() {
       binding.position.text = game.positionString()
        //Log.d("gameBoard","${game.stringForGame()}")
       binding.board.text = game.stringForGame()
    }
}