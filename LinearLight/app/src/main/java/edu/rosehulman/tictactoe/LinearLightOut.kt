package edu.rosehulman.tictactoe

import android.content.Context
import kotlin.random.Random

class LinearLightOut() {
    var board = IntArray(NUM_COLUMNS){ Random.nextInt(0, 1)}
    var state = GameState.Initial
    var trails = 0
    lateinit var context: Context

    constructor(context:Context):this(){
        this.context = context
    }

    fun reset(){
        trails = 0
        board = IntArray(NUM_COLUMNS){ Random.nextInt(0, 1)}
        state = GameState.Initial
    }


fun stringForGameState() = when (state){
        GameState.Initial -> context.getString(R.string.initial)//good
        GameState.Gaming -> "You have taken $trails turns"
        GameState.GameEnd -> context.getString(R.string.won)
    }


    fun stringForButtonAt( column : Int): String{
        if ( column in 0 until NUM_COLUMNS) {
            if (board[column] == 1) {
                return "1"
            } else if (board[column] == 0) {
                return "0"
            }
        }
        return ""
    }

    fun pressButtonAt( column: Int) {
        if(state == GameState.Initial){
            state = GameState.Gaming
        }else if(state == GameState.GameEnd){
            return
        }
        if(column==0){
            board[0] = if (board[column]==1) 0 else 1
            board[1] = if (board[column]==1) 0 else 1
        }else if(column==NUM_COLUMNS-1){
            board[NUM_COLUMNS-1] = if (board[column]==1) 0 else 1
            board[NUM_COLUMNS-2] = if (board[column]==1) 0 else 1
        }else{
            board[column-1] = if (board[column]==1) 0 else 1
            board[column] = if (board[column]==1) 0 else 1
            board[column+1] = if (board[column]==1) 0 else 1
        }
        checkForWin()

    }
    fun checkForWin() {
        if (didPieceWin()) {
            state = GameState.GameEnd
        }
    }



    private fun didPieceWin(): Boolean {
        var checker = board[0]
        for (column in 0 until 7) {
            if(board[column]!=checker)
                return false
        }
        return true
    }



    enum class GameState{
        Initial,
        Gaming,
        GameEnd,
    }


    companion object{
        const val NUM_COLUMNS = 7
    }
}