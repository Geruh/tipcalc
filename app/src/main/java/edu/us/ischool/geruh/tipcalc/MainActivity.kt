package edu.us.ischool.geruh.tipcalc

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import java.text.NumberFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    private var totalAmount = ""
    private val moneyFormat = NumberFormat.getCurrencyInstance(Locale.US)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tipBtn = findViewById<Button>(R.id.calculateTip)
        val amountField = findViewById<EditText>(R.id.totalMealCost)
        val selectPercent = findViewById<Spinner>(R.id.tipPercent)
        if (amountField.getText().toString() == "") {
            tipBtn.isEnabled = false
        }

        tipBtn.setOnClickListener {
            calcTip(selectPercent)
        }

        amountField.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                tipBtn.isEnabled = true
                if (s.toString() != totalAmount) {
                    amountField.removeTextChangedListener(this)
                    var cleanString = s.toString().replace("[$,.]".toRegex(), "")
                    var amountDbl = cleanString.toDouble()
                    var newAmount = moneyFormat.format((amountDbl / 100))
                    totalAmount = newAmount
                    amountField.setText(newAmount);
                    amountField.setSelection(newAmount.length)
                    amountField.addTextChangedListener(this)
                }
            }

        })
    }

    fun calcTip(selectPercent : Spinner) {
        var tipAmount: Double = (totalAmount.replace("[$,.]".toRegex(), "")).toDouble()
        val tipValue = selectPercent.selectedItem.toString().dropLast(1).toInt()
        tipAmount = (tipAmount / 100) * (tipValue * .01)
        val decimals: String = "%.2f".format(tipAmount)
        val formatted = moneyFormat.format(decimals.toDouble())
        Toast.makeText(applicationContext, "${formatted.toString()}", Toast.LENGTH_SHORT).show()
    }
}
