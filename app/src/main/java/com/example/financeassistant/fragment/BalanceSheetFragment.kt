package com.example.financeassistant.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.financeassistant.R
import com.example.financeassistant.databinding.FragmentBalanceSheetBinding
import com.example.financeassistant.incomeRecyclerView.OperationAdapter
import com.example.financeassistant.room.OperationApplication
import com.example.financeassistant.viewModels.BalanceViewModel
import com.example.financeassistant.viewModels.BalanceViewModelFactory
import com.google.android.material.chip.Chip
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class BalanceSheetFragment : Fragment() {

    private var binding: FragmentBalanceSheetBinding? = null
    private lateinit var operationAdapter: OperationAdapter
    private val viewModel: BalanceViewModel by viewModels {
        BalanceViewModelFactory((requireActivity().application as OperationApplication).repository)
    }

    //TODO - 1.a) kliknięcie FAB pokazuje ukryty FilterView, dodać jakąś ładną animację -> dynamiczne rozjechany
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_balance_sheet, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBalanceSheetBinding.bind(view)
        operationAdapter = OperationAdapter(requireContext())
        setRecyclerView()
        setObservers()

        val spinnerForm: Spinner = binding?.categoryBalanceInput!!
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.dates_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerForm.adapter = adapter
        }

        binding?.idFABbalance?.setOnClickListener {

//            var animation = AnimationUtils.loadAnimation(context, R.anim.fade_in)
//            binding?.filtersHolder?.startAnimation(animation)
            binding?.filtersHolder?.visibility = View.VISIBLE
//            animation = AnimationUtils.loadAnimation(context, R.anim.fade_out)
//            binding?.recyclerview?.startAnimation(animation)
            binding?.recyclerview?.visibility = View.GONE

            spinnerForm.onItemSelectedListener = object : AdapterView
            .OnItemSelectedListener {
                @RequiresApi(Build.VERSION_CODES.O)
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    when(position){
                        4 -> { binding?.specificRangeHolder?.visibility = View.VISIBLE }
                        else -> { binding?.specificRangeHolder?.visibility = View.GONE }
                    }
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }

        //TODO - 1.b) https://www.geeksforgeeks.org/how-to-show-and-hide-a-view-with-a-slide-up-and-down-animation-in-android/
        binding?.filterButton?.setOnClickListener(
            object: View.OnClickListener{
                override fun onClick(p0: View?) {
                    var checkedChips = listOf<String>()

                    val ids: List<Int> = binding?.chipGroup?.checkedChipIds as List<Int>
                    for (id in ids) {
                        val chip: Chip? = binding?.chipGroup?.findViewById(id)
                        checkedChips = checkedChips.plusElement(chip?.text.toString())
                    }

                    when(spinnerForm.selectedItemPosition){
                        0 -> { filterInDatabase(1, checkedChips) }
                        1 -> { filterInDatabase(7, checkedChips) }
                        2 -> { filterInDatabase(30,checkedChips) }
                        3 -> {
                            when {
                                LocalDate.now().isLeapYear ->  filterInDatabase(366, checkedChips)
                                else -> filterInDatabase(365, checkedChips)
                            }
                        }
                        4 -> {
                            val startDate = parseStringToDate(binding?.startDate?.text.toString())
                            val endDate = parseStringToDate(binding?.startDate?.text.toString())
                            if(binding?.startDate?.text.toString() == "" || binding?.startDate?.text.toString() == ""){
                                filterInDatabase(1, checkedChips)
                            } else {
                                viewModel.filter(
                                    binding?.amountMin?.text.toString().toDouble(),
                                    binding?.amountMax?.text.toString().toDouble(),
                                    startDate.toEpochDay(),
                                    endDate.toEpochDay(),
                                    checkedChips
                                )
                            }
                        }
                    }

//                    var animation = AnimationUtils.loadAnimation(context, R.anim.fade_out)
//                    binding?.filtersHolder?.startAnimation(animation)
                    binding?.filtersHolder?.visibility = View.GONE
//                    animation = AnimationUtils.loadAnimation(context, R.anim.fade_in)
//                    binding?.recyclerview?.startAnimation(animation)
                    binding?.recyclerview?.visibility = View.VISIBLE
                }
            }
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun parseStringToDate(minOrMaxDate: String): LocalDate {
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH)
        return LocalDate.parse(minOrMaxDate, formatter)
    }

    private fun setRecyclerView() {
        binding?.recyclerview?.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = operationAdapter
        }
    }

    private fun setObservers() {
        viewModel.allResults.observe(viewLifecycleOwner) { results ->
            operationAdapter.setItemList(results)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun filterInDatabase(daysToSubtract: Long, chips: List<String>){
        LocalDate.now().minusDays(daysToSubtract)
        viewModel.filter(
            binding?.amountMin?.text.toString().toDouble(),
            binding?.amountMax?.text.toString().toDouble(),
            LocalDate.now().minusDays(daysToSubtract).toEpochDay(),
            LocalDate.now().toEpochDay(),
            chips)
    }
}