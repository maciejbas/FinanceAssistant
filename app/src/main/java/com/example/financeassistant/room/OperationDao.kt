package com.example.financeassistant.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface OperationDao {

    //TODO - zmienić tę funkcję na sortowanie po polu date
    //TODO - dodać pole date do Rooma
    @Query("SELECT * FROM operation_table ORDER BY operation_type ASC")
    fun getAlphabetizedWords(): Flow<List<Operation>>

    //TODO - zrobić filtrowanie
    @Query("SELECT * FROM operation_table " +
            "WHERE amount BETWEEN :minAmount AND :maxAmount " +
            "AND date BETWEEN :minDate AND :maxDate " +
            "AND category in (:categories)")
    fun getFiltered(minAmount: Double,
                    maxAmount: Double,
                    minDate: Long,
                    maxDate: Long,
                    categories: List<String>): Flow<List<Operation>>

    @Query("SELECT * FROM operation_table WHERE operation_type='INCOME'")
    fun getIncomes(): Flow<List<Operation>>

    @Query("SELECT * FROM operation_table WHERE operation_type='EXPENSE'")
    fun getExpenses(): Flow<List<Operation>>

    @Query("SELECT * FROM operation_table")
    fun getResults(): Flow<List<Operation>>

    @Query("SELECT * FROM operation_table ORDER BY category ASC")
    fun getByCategoryAsc(): Flow<List<Operation>>

    @Query("SELECT * FROM operation_table ORDER BY category DESC")
    fun getByCategoryDesc(): Flow<List<Operation>>

    @Query("SELECT * FROM operation_table ORDER BY id ASC")
    fun getByIdAsc(): Flow<List<Operation>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(operation: Operation)

    @Query("DELETE FROM operation_table")
    suspend fun deleteAll()

    @Query("DELETE FROM operation_table WHERE id = :id")
    suspend fun deleteById(id: Int)
}