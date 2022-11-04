package com.example.financeassistant.viewModels

import androidx.lifecycle.*
import com.example.financeassistant.room.Operation
import com.example.financeassistant.room.OperationRepository
import kotlinx.coroutines.launch

class BalanceViewModel(private val repository: OperationRepository) : ViewModel() {

    val allResults: LiveData<List<Operation>> = repository.allResults.asLiveData()
    //val allFiltered: LiveData<List<Operation>> = repository.allFiltered.asLiveData()

    fun filter(minAmount: Double, maxAmount: Double, minDate: Long, maxDate: Long, categories: List<String>) = viewModelScope.launch {
        repository.filter(minAmount, maxAmount, minDate, maxDate, categories)
    }

    fun insert(income: Operation) = viewModelScope.launch {
        repository.insert(income)
    }
}

class BalanceViewModelFactory(private val repository: OperationRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BalanceViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BalanceViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}