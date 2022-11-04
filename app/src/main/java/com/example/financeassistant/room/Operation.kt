package com.example.financeassistant.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "operation_table")
data class Operation(

    @ColumnInfo(name = "category") val category: String,
    @ColumnInfo(name = "amount") val amount: Double,
    //TODO - zmienić date: String na date: Date??
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "operation_type") val operationType: OperationType,
    @PrimaryKey(autoGenerate = true) val id: Int = 0

)