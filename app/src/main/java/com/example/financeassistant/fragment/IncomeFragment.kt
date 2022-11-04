package com.example.financeassistant.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.financeassistant.R
import com.example.financeassistant.databinding.FragmentIncomeBinding
import com.example.financeassistant.dialogs.errorDialog.ErrorDialog
import com.example.financeassistant.dialogs.inputDialog.DialogCallback
import com.example.financeassistant.dialogs.inputDialog.InputDialog
import com.example.financeassistant.incomeRecyclerView.OperationAdapter
import com.example.financeassistant.room.Operation
import com.example.financeassistant.room.OperationApplication
import com.example.financeassistant.room.OperationType
import com.example.financeassistant.viewModels.IncomeViewModel
import com.example.financeassistant.viewModels.IncomeViewModelFactory

class IncomeFragment : Fragment(R.layout.fragment_income) {

    private var binding: FragmentIncomeBinding? = null
    private lateinit var operationAdapter: OperationAdapter
    private val viewModel: IncomeViewModel by viewModels {
        IncomeViewModelFactory((requireActivity().application as OperationApplication).repository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentIncomeBinding.bind(view)
        operationAdapter = OperationAdapter(requireContext())
        setRecyclerView()
        setObservers()
        setListeners()
    }

    private fun setRecyclerView() {
        binding?.recyclerview?.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = operationAdapter
        }
    }

    private fun setObservers() {
        viewModel.allIncomes.observe(viewLifecycleOwner) { incomes ->
            operationAdapter.setItemList(incomes)
        }
    }

    private fun setListeners() {
        binding?.idFABaddIncome?.setOnClickListener {
            InputDialog().build(
                requireContext(),
                object : DialogCallback {
                    override fun onClose(category: String, amount: String, date: String) {
                        if (category.isEmpty() || amount.isEmpty() || date.isEmpty()) {
                            ErrorDialog().build(requireContext())
                        } else {
                            viewModel.insert(Operation(category, amount.toDouble(), date, OperationType.INCOME))
                        }
                    }
                }
            )
        }
    }
}