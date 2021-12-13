package edu.rosehulman.tictactoe


import LinearLightOutViewModel
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import edu.rosehulman.tictactoe.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var tttButons: Array<Button>
    private lateinit var startButton: Button
    private lateinit var emailButton: Button
    private lateinit var game: LinearLightOut
    private lateinit var model: LinearLightOutViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        model = ViewModelProvider(this).get(LinearLightOutViewModel::class.java)
        tttButons = Array(LinearLightOut.NUM_COLUMNS){ Button(this) }
        startButton = Button(this)
        emailButton = Button(this)
        game = LinearLightOut(this)
        if(savedInstanceState != null){
            game.board = savedInstanceState.getIntArray("lights")!!
            game.trails = savedInstanceState.getInt("trailNum")
            game.state = LinearLightOut.GameState.valueOf(savedInstanceState.getSerializable("gameState").toString())

        }
        setupButtons()
        updateView()
    }

    public override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        game.populateBundle(outState) // write this method. In it,
        // save off data individually using outState?.putXX(key, value)
        // Hint: you will use the appropriate methods to store an Int,
        // Int[] or Boolean[] and maybe a String.
    }

        private fun updateView() {
        binding.gameStateTextView.text = game.stringForGameState()
            for(col in 0 until LinearLightOut.NUM_COLUMNS) {
                tttButons[col].text = game.stringForButtonAt(col)
//                tttButons[col].isEnabled = (game.state!=LinearLightOut.GameState.GameEnd)
//                tttButons[col].isClickable = (game.state!=LinearLightOut.GameState.GameEnd)
                    if(game.state==LinearLightOut.GameState.GameEnd){
                        tttButons[col].isEnabled = false
                        tttButons[col].isClickable = false
                        tttButons[col].setBackgroundColor(Color.WHITE)
                    }else{
                        tttButons[col].isEnabled = true
                        tttButons[col].isClickable = true
                        tttButons[col].setBackgroundColor(Color.GRAY)
                    }

                }
    }

    private fun setupButtons() {
            for(col in 0 until LinearLightOut.NUM_COLUMNS){

                //tttButons[0][0] = binding.button00

                val id = resources.getIdentifier("button0$col", "id",packageName)

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
        binding.emailButton.setOnClickListener {
            // From https://developer.android.com/guide/components/intents-common#ComposeEmail
            composeEmail(arrayOf("boutell@gmail.com"), "Demo app")
        }


    }

    private fun composeEmail(addresses: Array<String>, subject: String) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "*/*"
            putExtra(Intent.EXTRA_EMAIL, addresses)
            putExtra(Intent.EXTRA_SUBJECT, subject)
            if(game.state == LinearLightOut.GameState.GameEnd){
                putExtra(Intent.EXTRA_TEXT, "The player won the game!")
            }else{
                putExtra(Intent.EXTRA_TEXT, "The player is currently in game with ${game.trails} trails")
            }

        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }
}