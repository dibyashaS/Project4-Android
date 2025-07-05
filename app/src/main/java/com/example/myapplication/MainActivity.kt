package com.example.myapplication
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val billInput = findViewById<EditText>(R.id.bill_input)
        val tipSeekBar = findViewById<SeekBar>(R.id.seekBar)
        val tipPercentText = findViewById<TextView>(R.id.tipPercentText)
        val totalOutput = findViewById<TextView>(R.id.totalOutput)

        // Trigger calculation when SeekBar changes
        tipSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                tipPercentText.text = "Tip: $progress%"
                calculateTotal(billInput.text.toString(), progress, totalOutput)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        // Trigger calculation when typing in EditText
        billInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val progress = tipSeekBar.progress
                calculateTotal(s.toString(), progress, totalOutput)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun calculateTotal(billStr: String, tipPercent: Int, totalOutput: TextView) {
        if (billStr.isEmpty()) {
            totalOutput.text = "Total: $0.00"
            return
        }

        val bill = billStr.toDoubleOrNull()
        if (bill == null) {
            totalOutput.text = "Invalid bill input"
            return
        }

        val tip = bill * tipPercent / 100
        val total = bill + tip
        totalOutput.text = "Total: $%.2f".format(total)
    }
}
