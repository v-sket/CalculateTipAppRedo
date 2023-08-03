package com.example.calculatetipredo

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.core.content.ContextCompat

private const val TAG = "MainActivity"
private const val INITIAL_TIP_PERCENT = 15

class MainActivity : AppCompatActivity() {
    private lateinit var tvBaseAmount: TextView
    private lateinit var tvTipPercent: TextView
    private lateinit var etBaseAmount: EditText
    private lateinit var sbTipPercent: SeekBar
    private lateinit var tvTipAmountResult: TextView
    private lateinit var tvTotalAmountResult: TextView
    private lateinit var tvDescription: TextView

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvBaseAmount = findViewById(R.id.tvBaseAmount)
        tvTipPercent = findViewById(R.id.tvTipPercent)
        etBaseAmount = findViewById(R.id.etBaseAmount)
        sbTipPercent = findViewById(R.id.sbTipPercent)
        tvTipAmountResult = findViewById(R.id.tvTipAmountResult)
        tvTotalAmountResult = findViewById(R.id.tvTotalAmountResult)
        tvDescription = findViewById(R.id.tvDescription)

        sbTipPercent.progress = INITIAL_TIP_PERCENT
        tvTipPercent.text = "$INITIAL_TIP_PERCENT%"

        sbTipPercent.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            // Want to create override fun need "object:..."
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                Log.i(TAG, "onProgressChanged $progress")
                tvTipPercent.text = "$progress%"
                computeResult()
                updateTipDescription(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        //have to put etBaseAmount.addTextChangedListener after sbTipPercent.setOnSeekBarChangeListener to calculate TipAmount
        etBaseAmount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                Log.i(TAG, "afterChanged $s")
                computeResult()
            }

        })
    }

    //    @SuppressLint("SetTextI18n")
    @SuppressLint("SetTextI18n")
    private fun computeResult() {
        if (etBaseAmount.text.isEmpty()) {
            tvTipAmountResult.text = ""
            tvTotalAmountResult.text = ""
            return
        }

        val baseAmount = etBaseAmount.text.toString().toDouble()
        val tipPercent = sbTipPercent.progress
        val tipAmount = baseAmount * tipPercent / 100
        val totalAmount = baseAmount + tipAmount

        tvTipAmountResult.text = "%.2f".format(tipAmount)
        tvTotalAmountResult.text = "%.2f".format(totalAmount)
    }

    private fun updateTipDescription(tipPercent: Int) {
        val tipDescription = when (tipPercent) {
            in 0..4 -> "Poor"
            in 5..9 -> "Acceptable"
            in 10..14 -> "Good"
            in 15..19 -> "Great"
            else -> "Amazing"
        }
        tvDescription.text = tipDescription
        updateColor(tipPercent)
    }

    private fun updateColor(tipPercent:Int){
        when(tipPercent){
            in 0..4 -> tvDescription.setTextColor(ContextCompat.getColor(this, R.color.red))
            in 5..9 -> tvDescription.setTextColor(ContextCompat.getColor(this, R.color.orange))
            in 10..14 -> tvDescription.setTextColor(ContextCompat.getColor(this, R.color.yellow))
            in 15..19 -> tvDescription.setTextColor(ContextCompat.getColor(this, R.color.yellowGreen))
            else -> tvDescription.setTextColor(ContextCompat.getColor(this, R.color.green))
        }
    }
}




