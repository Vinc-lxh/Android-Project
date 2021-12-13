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
        val game = LinearLightOut()
        for(row in 0 until LinearLightOut.NUM_ROWS){
            for(column in 0 until LinearLightOut.NUM_COLUMNS) {
                assertEquals("",game.stringForButtonAt(row,column))
            }
        }

        assertEquals(4, 2 + 2)
    }


    @Test
    fun press() {
        val game = LinearLightOut()
        game.pressButtonAt(1, 2)
        assertEquals("X", game.stringForButtonAt(1, 2))
        assertEquals(LinearLightOut.GameState.O_TURN, game.state)
        game.pressButtonAt(2, 2)
        assertEquals("O", game.stringForButtonAt(2, 2))
        assertEquals(LinearLightOut.GameState.X_TURN, game.state)
        game.pressButtonAt(2, 2)
        assertEquals("O", game.stringForButtonAt(2, 2))
        assertEquals(LinearLightOut.GameState.X_TURN, game.state)
    }

    @Test
    fun pressOutOfBoundsIsIgnored() {
        val game = LinearLightOut()
        assertEquals(LinearLightOut.GameState.X_TURN, game.state)
        game.pressButtonAt(3, 1)
        assertEquals(LinearLightOut.GameState.X_TURN, game.state)
        game.pressButtonAt(-1, 1)
        game.pressButtonAt(1, 3)
        game.pressButtonAt(1, -1)
        assertEquals(LinearLightOut.GameState.X_TURN, game.state)
    }

}