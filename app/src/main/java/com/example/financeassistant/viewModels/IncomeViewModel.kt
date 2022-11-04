package com.example.financeassistant.viewModels

import androidx.lifecycle.*
import com.example.financeassistant.room.Operation
import com.example.financeassistant.room.OperationRepository
import kotlinx.coroutines.launch

class IncomeViewModel(private val repository: OperationRepository) : ViewModel() {

    val allIncomes: LiveData<List<Operation>> = repository.allIncomes.asLiveData()

    fun insert(income: Operation) = viewModelScope.launch {
        repository.insert(income)
    }
}

class IncomeViewModelFactory(private val repository: OperationRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(IncomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return IncomeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}