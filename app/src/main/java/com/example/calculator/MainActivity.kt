package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var firstNum: Double = 0.0
    var secondNum: Double = 0.0
    var lastNum = false
    var dotUsed = false
    var opUsed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    fun onDigit (view: View) {
        display.append((view as Button).text)
        lastNum = true
    }
    fun onClear (view: View) {
        display.text = ""
        lastNum = false
        dotUsed = false
        opUsed = false
    }
    fun onDot (view: View) {
        if(lastNum && !dotUsed) {
            display.append((view as Button).text)
            lastNum = false
            dotUsed = true
        }
    }
    fun onOperator (view: View) {
        var op = (view as Button).text.toString()
        if(canAddOperator(op)) {
            if(negativeNumber(op)){
                display.append(op)
                opUsed = false
                lastNum = false
                dotUsed = false
            } else if(!negativeNumber(op)){
                display.append(op)
                opUsed = true
                lastNum = false
                dotUsed = false
            }
        }
    }
    fun onCalculate (view: View) {
        var res = 0.0
        var text = display.text.toString()

        val regex = """^\-?[0-9]+(\.[0-9]+)*(\/|\+|\-|\*)\-?[0-9]+(\.[0-9]+)*$""".toRegex()
        if(regex.matches(input = display.text.toString())){
            if(text.contains("+")){
                var splitted = text.split("+")
                res = splitted[0].toDouble() + splitted[1].toDouble()
            } else if(text.contains("*")){
                var splitted = text.split("*")
                res = splitted[0].toDouble() * splitted[1].toDouble()
            } else if(text.contains("/")){
                var splitted = text.split("/")
                res = splitted[0].toDouble() / splitted[1].toDouble()
            } else if(text.lastIndexOf("-") != 0){
                if(text[0].toString() == "-") {
                    var withoutFirstMinus = text.substring(1);
                    var splitted = withoutFirstMinus.split("-")
                    res = - splitted[0].toDouble() - splitted[1].toDouble()
                } else if(!text[0].equals("-")) {
                    Toast.makeText(this, text[0].equals("-").toString(), Toast.LENGTH_LONG).show()
                    var splitted = text.split("-")
                    res = splitted[0].toDouble() - splitted[1].toDouble()
                }
            }
            display.setText(res.toString())
            lastNum = true
            opUsed = false
            dotUsed = true
        } else {
            Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show()
        }

    }
    private fun canAddOperator (op: String): Boolean {
        var text = display.text.toString()
        if((text.isEmpty() && op == "-") || (lastNum && !opUsed)){
            return true
        }
        return false
    }
    private fun negativeNumber (op: String):Boolean {
        if(display.text.toString().isEmpty() && op == "-") {

            return true
        }
        return false
    }


}
