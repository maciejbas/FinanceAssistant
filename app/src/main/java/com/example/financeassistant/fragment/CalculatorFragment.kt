package com.example.financeassistant.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.financeassistant.R

class CalculatorFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_calculator, container, false)

        val spinnerFrom: Spinner = root.findViewById(R.id.topCurrencyNameHolder)
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.currencies_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerFrom.adapter = adapter
        }

        val spinnerTo: Spinner = root.findViewById(R.id.bottomCurrencyNameHolder)
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.currencies_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerTo.adapter = adapter
        }

        val cb: Button = root.findViewById(R.id.calculateButton)
        cb.setOnClickListener {
            val calculateFrom: EditText = root.findViewById(R.id.calculateFrom)
            val calculateTo: TextView = root.findViewById(R.id.calculateTo)
            val multiply = calculateFrom.text.toString().toDouble() * 4
            calculateTo.text = multiply.toString()
        }

        val ib: ImageButton = root.findViewById(R.id.imageButton)
        ib.setOnClickListener {
            ib.animate().setDuration(300).rotationBy(180f).start()
            val spinnerFromValue = spinnerFrom.selectedItemPosition
            val spinnerToValue = spinnerTo.selectedItemPosition
            spinnerFrom.setSelection(spinnerToValue)
            spinnerTo.setSelection(spinnerFromValue)
        }

        return root
    }
}