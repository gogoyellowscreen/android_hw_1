package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        const val TAG = "MainActivity"
        const val CUR_NUM_KEY = "CUR_NUM_KEY"
        const val LEFT_OPERAND_KEY = "LEFT_OPERAND_KEY"
        const val CUR_OP_KEY = "CUR_OP_KEY"
    }

    var curNumStr: String = ""
    var leftOperand: String = ""
    var curOp: Char = '$'

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i(TAG, "onCreate")

        zero.setOnClickListener(this)
        one.setOnClickListener(this)
        two.setOnClickListener(this)
        three.setOnClickListener(this)
        four.setOnClickListener(this)
        five.setOnClickListener(this)
        six.setOnClickListener(this)
        seven.setOnClickListener(this)
        eight.setOnClickListener(this)
        nine.setOnClickListener(this)
        dot.setOnClickListener(this)
        eq.setOnClickListener(this)
        cancel.setOnClickListener(this)
        plus.setOnClickListener(this)
        minus.setOnClickListener(this)
        mul.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            zero -> curNumStr = if (curNumStr.isNotEmpty()) curNumStr + "0" else curNumStr
            one -> curNumStr += "1"
            two -> curNumStr += "2"
            three -> curNumStr += "3"
            four -> curNumStr += "4"
            five -> curNumStr += "5"
            six -> curNumStr += "6"
            seven -> curNumStr += "7"
            eight -> curNumStr += "8"
            nine -> curNumStr += "9"
            dot -> {
                if (curNumStr.isEmpty()) {
                  curNumStr = "0."
                } else if (!curNumStr.contains('.')) {
                    curNumStr += "."
                }
            }
            eq -> {
                solveCur()
                curOp = '='
            }
            cancel -> {
                curNumStr = ""
                leftOperand = ""
                curOp = '$'
            }
            plus -> {
                solveCur()
                curOp = '+'
            }
            minus -> {
                solveCur()
                curOp = '-'
            }
            mul -> {
                solveCur()
                curOp = '*'
            }
        }
        updateLabel()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        Log.i(TAG, "onSaveInstanceState")
        outState.putString(CUR_NUM_KEY, curNumStr)
        outState.putChar(CUR_OP_KEY, curOp)
        outState.putString(LEFT_OPERAND_KEY, leftOperand)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.i(TAG, "onRestoreInstanceState")
        curNumStr = savedInstanceState.getString(CUR_NUM_KEY).toString()
        leftOperand = savedInstanceState.getString(LEFT_OPERAND_KEY).toString()
        curOp = savedInstanceState.getChar(CUR_OP_KEY)
        updateLabel()
    }

    private fun solveCur() {
        if (curNumStr.isEmpty()) {
            return
        }
        val lOp: Double = if (leftOperand.isEmpty()) 0.0 else leftOperand.toDouble()
        val rOp: Double = curNumStr.toDouble()
        when (curOp) {
            '+' -> curNumStr = (lOp + rOp).toString()
            '-' -> curNumStr = (lOp - rOp).toString()
            '*' -> curNumStr = (lOp * rOp).toString()
        }
        if (curNumStr.endsWith(".0")) curNumStr = curNumStr.substring(0, curNumStr.length - 2)
        leftOperand = curNumStr
        curNumStr = ""
    }

    private fun updateLabel() {
        when {
            curNumStr.isEmpty() && leftOperand.isEmpty() -> cur_res.text = "0"
            curNumStr.isEmpty() -> cur_res.text = leftOperand
            else -> cur_res.text = curNumStr
        }
    }
}