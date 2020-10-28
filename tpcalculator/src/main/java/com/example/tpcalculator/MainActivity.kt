package com.example.tpcalculator

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import net.objecthunter.exp4j.ExpressionBuilder
import com.example.tpcalculator.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    //private lateinit var binding: ActivityMainBinding

    lateinit var operationText: TextView
    lateinit var solution: TextView
    lateinit var history: TextView
    var lastDigit: Boolean = false
    var error: Boolean = false
    var dot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        /*binding = ActivityMainBinding.inflate(LayoutInflater.from(baseContext))
        setContentView(binding.root)*/


        setContentView(R.layout.activity_main)
        operationText = findViewById(R.id.solution)
        //operationText = binding.solution
        solution = findViewById(R.id.res)
        //solution = binding.res
        history = findViewById(R.id.history)
        //history = binding.history
        history.setVisibility(View.GONE)
    }


    fun onNumClick(view: View) {

        if (error) {
            operationText.text = (view as Button).text
            error = false
        } else {
            operationText.append((view as Button).text)
            //operationText.text = (view as Button).text
        }
        lastDigit = true
    }

    fun onClearClick(view: View) {

        this.operationText.text = ""
        solution.text=""
        lastDigit = false
        error = false
        dot = false
    }

    fun onDotClick(view: View) {

        if (lastDigit && !error && !dot) {
            operationText.append(".")
            lastDigit = false
            dot = true
        }
    }

    fun onOperatorClick(view: View) {

        if (lastDigit && !error) {
            operationText.append((view as Button).text)
            lastDigit = false
            dot = false
        }
    }

    fun onHistoryClick(view: View){
        history.setVisibility(View.VISIBLE)
    }

    fun onEqualClick(view: View) {

        if (lastDigit && !error) {

            val string = operationText.text.toString()
            val expression = ExpressionBuilder(string).build()
            try {
                val result = expression.evaluate()
                solution.setTextColor(Color.GRAY)
                solution.text=""
                solution.append("=")
                solution.append(result.toString())

                history.append(string)
                history.append("=")
                history.append(result.toString())
                history.append("\n")

                dot = true
            } catch (ex: ArithmeticException) {
                operationText.text = "Wrong Expression"
                error = true
                lastDigit = false
            }
        }
    }
}