package edu.rosehulman.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import edu.rosehulman.tictactoe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var tttButons: Array<Button>
    private lateinit var startButton: Button
    private lateinit var emailButton: Button
    private lateinit var game: LinearLightOut

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        tttButons = Array(LinearLightOut.NUM_COLUMNS){ Button(this) }
        startButton = Button(this)
        emailButton = Button(this)
        game = LinearLightOut(this)
        setupButtons()
        updateView()
    }

    private fun updateView() {
        binding.gameStateTextView.text = game.stringForGameState()
            for(col in 0 until LinearLightOut.NUM_COLUMNS) {
                tttButons[col].text = game.stringForButtonAt(col)
                }
    }

    private fun setupButtons() {
            for(col in 0 until LinearLightOut.NUM_COLUMNS){

                //tttButons[0][0] = binding.button00

                val id = resources.getIdentifier("button$col", "id",packageName)

                tttButons[col] = binding.root.findViewById<Button>(id)

                tttButons[col].setOnClickListener{
                    Log.d("TTT", "Button , $col was pressed")
                    game.pressButtonAt(col)
                    updateView()
                }
            }


        binding.newGameButton.setOnClickListener{
            game.reset()
            Log.d("TTT","resetting game")
            updateView()
        }
        binding.emailButton.setOnClickListener{
            Log.d("TTT","emailing")
            updateView()
        }

    }
}