package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        const val TAG = "MainActivity"
        const val CUR_NUM_KEY = "CUR_NUM_KEY"
        const val LEFT_OPERAND_KEY = "LEFT_OPERAND_KEY"
        const val CUR_OP_KEY = "CUR_OP_KEY"
    }

    lateinit var zero: Button
    lateinit var one: Button
    lateinit var two: Button
    lateinit var three: Button
    lateinit var four: Button
    lateinit var five: Button
    lateinit var six: Button
    lateinit var seven: Button
    lateinit var eight: Button
    lateinit var nine: Button
    lateinit var dot: Button
    lateinit var eq: Button
    lateinit var plus: Button
    lateinit var minus: Button
    lateinit var mul: Button
    lateinit var cancel: Button
    lateinit var curRes: TextView
    var curNumStr: String = ""
    var leftOperand: String = ""
    var curOp: Char = '$'
    lateinit var lastView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i(TAG, "onCreate")

        zero = findViewById(R.id.zero)
        one = findViewById(R.id.one)
        two = findViewById(R.id.two)
        three = findViewById(R.id.three)
        four = findViewById(R.id.four)
        five = findViewById(R.id.five)
        six = findViewById(R.id.six)
        seven = findViewById(R.id.seven)
        eight = findViewById(R.id.eight)
        nine = findViewById(R.id.nine)
        dot = findViewById(R.id.dot)
        eq = findViewById(R.id.eq)
        cancel = findViewById(R.id.cancel)
        plus = findViewById(R.id.plus)
        minus = findViewById(R.id.minus)
        mul = findViewById(R.id.multiply)
        curRes = findViewById(R.id.cur_res)
        lastView = zero

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
        lastView = v
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
        if (lastView == plus || lastView == minus || lastView == eq || lastView == mul) {
            return
        }
        val lOp: Double = if (leftOperand.isEmpty()) 0.0 else leftOperand.toDouble()
        val rOp: Double = if (curNumStr.isEmpty()) 0.0 else curNumStr.toDouble()
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
            curNumStr.isEmpty() && leftOperand.isEmpty() -> curRes.text = "0"
            curNumStr.isEmpty() -> curRes.text = leftOperand
            else -> curRes.text = curNumStr
        }
    }
}