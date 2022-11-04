package com.example.financeassistant.viewModels

import androidx.lifecycle.*
import com.example.financeassistant.room.Operation
import com.example.financeassistant.room.OperationRepository
import kotlinx.coroutines.launch

class ExpenseViewModel(private val repository: OperationRepository) : ViewModel() {

    //TODO - add button inserts data to DAO
    val allExpenses: LiveData<List<Operation>> = repository.allExpenses.asLiveData()

    fun insert(income: Operation) = viewModelScope.launch {
        repository.insert(income)
    }
}

class ExpenseViewModelFactory(private val repository: OperationRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExpenseViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ExpenseViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}