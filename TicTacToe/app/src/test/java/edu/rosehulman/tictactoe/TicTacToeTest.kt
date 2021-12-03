package edu.rosehulman.tictactoe

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class TicTacToeTest {
    @Test
    fun reset() {
        val game = TicTacToeGame()
        for(row in 0 until TicTacToeGame.NUM_ROWS){
            for(column in 0 until TicTacToeGame.NUM_COLUMNS) {
                assertEquals("",game.stringForButtonAt(row,column))
            }
        }

        assertEquals(4, 2 + 2)
    }


    @Test
    fun press() {
        val game = TicTacToeGame()
        game.pressButtonAt(1, 2)
        assertEquals("X", game.stringForButtonAt(1, 2))
        assertEquals(TicTacToeGame.GameState.O_TURN, game.state)
        game.pressButtonAt(2, 2)
        assertEquals("O", game.stringForButtonAt(2, 2))
        assertEquals(TicTacToeGame.GameState.X_TURN, game.state)
        game.pressButtonAt(2, 2)
        assertEquals("O", game.stringForButtonAt(2, 2))
        assertEquals(TicTacToeGame.GameState.X_TURN, game.state)
    }

    @Test
    fun pressOutOfBoundsIsIgnored() {
        val game = TicTacToeGame()
        assertEquals(TicTacToeGame.GameState.X_TURN, game.state)
        game.pressButtonAt(3, 1)
        assertEquals(TicTacToeGame.GameState.X_TURN, game.state)
        game.pressButtonAt(-1, 1)
        game.pressButtonAt(1, 3)
        game.pressButtonAt(1, -1)
        assertEquals(TicTacToeGame.GameState.X_TURN, game.state)
    }

}