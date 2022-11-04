package com.example.financeassistant.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.financeassistant.R
import com.example.financeassistant.databinding.FragmentExpensesBinding
import com.example.financeassistant.dialogs.errorDialog.ErrorDialog
import com.example.financeassistant.dialogs.inputDialog.DialogCallback
import com.example.financeassistant.dialogs.inputDialog.InputDialog
import com.example.financeassistant.incomeRecyclerView.OperationAdapter
import com.example.financeassistant.room.Operation
import com.example.financeassistant.room.OperationApplication
import com.example.financeassistant.room.OperationType
import com.example.financeassistant.viewModels.ExpenseViewModel
import com.example.financeassistant.viewModels.ExpenseViewModelFactory

class ExpensesFragment : Fragment(R.layout.fragment_expenses) {

    private var binding: FragmentExpensesBinding? = null
    private lateinit var operationAdapter: OperationAdapter
    private val viewModel: ExpenseViewModel by viewModels {
        ExpenseViewModelFactory((requireActivity().application as OperationApplication).repository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentExpensesBinding.bind(view)
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
        viewModel.allExpenses.observe(viewLifecycleOwner) { expenses ->
            operationAdapter.setItemList(expenses)
        }
    }

    private fun setListeners() {
        binding?.idFABaddExpenses?.setOnClickListener {
            InputDialog().build(
                requireContext(),
                object : DialogCallback {
                    override fun onClose(category: String, amount: String, date: String) {
                        if (category.isEmpty() || amount.isEmpty() || date.isEmpty()) {
                            ErrorDialog().build(requireContext())
                        } else {
                            viewModel.insert(
                                Operation(
                                    category,
                                    amount.toDouble(),
                                    date,
                                    OperationType.EXPENSE
                                )
                            )
                        }
                    }
                }
            )
        }
    }
}