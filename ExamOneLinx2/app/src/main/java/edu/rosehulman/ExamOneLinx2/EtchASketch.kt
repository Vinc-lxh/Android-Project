package edu.rosehulman.ExamOneLinx2

import android.content.Context
import android.util.Log
import java.lang.StringBuilder

class EtchASketch() {
    var board = Array(9){CharArray(10)}
    private var xpos = 4
    private var ypos = 4
    private var draw = false
    lateinit var context: Context

    constructor(context: Context):this(){
        this.context = context
        for(row in 0 until 9){
            for(col in 0 until 9){
                board[row][col] = '.'
            }
            board[row][9] = '\n'
        }
    }

    fun reset(){
        for(row in 0 until 9){
            for(col in 0 until 9){
                board[row][col] = '.'
            }
            board[row][9] = '\n'
        }
        draw = false
        xpos = 4
        ypos = 4
    }

    fun pressButtonAt(whichButton : Int){
        if(draw){
            board[ypos][xpos] = 'X'
        }
        when(whichButton){
            0 -> ypos = if(ypos>0) ypos - 1 else ypos   //up
            1 -> ypos = if(ypos<8) ypos + 1 else ypos   //down
            2 -> xpos = if(xpos>0) xpos - 1 else xpos   //left
            3 -> xpos = if(xpos<8) xpos + 1 else xpos   //right
        }

    }
    fun positionString():String{
        return "($xpos,$ypos)"
    }

    fun stringForGame():String{
        val sb = StringBuilder()
        for(row in 0 until 9){
            if(row == ypos){
                if(draw) sb.append(String(board[row]).replaceRange(xpos,xpos+1,"D"))
                else  sb.append(String(board[row]).replaceRange(xpos,xpos+1,"U"))
            }else{
                sb.append(String(board[row]))
            }

        }
        return sb.toString();
    }
    fun drawing(){
        draw=!draw
    }
}