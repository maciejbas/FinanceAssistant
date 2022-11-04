package com.example.financeassistant.room

import kotlinx.coroutines.flow.Flow

class OperationRepository(private val operationDao: OperationDao) {

    val allIncomes: Flow<List<Operation>> = operationDao.getIncomes()
    val allExpenses: Flow<List<Operation>> = operationDao.getExpenses()
    val allResults: Flow<List<Operation>> = operationDao.getResults()

    suspend fun filter(minAmount: Double, maxAmount: Double, minDate: Long, maxDate: Long, categories: List<String>){
        allResults.collect {
            operationDao.getFiltered(minAmount, maxAmount, minDate, maxDate, categories)
        }
    }

    suspend fun insert(operation: Operation) {
        operationDao.insert(operation)
    }
}