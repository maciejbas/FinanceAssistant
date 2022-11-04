package com.example.financeassistant.room

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class OperationApplication : Application() {

    private val applicationScope = CoroutineScope(SupervisorJob())

    private val database by lazy { OperationRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { OperationRepository(database.operationDao()) }
}