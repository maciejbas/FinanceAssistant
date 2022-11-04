package com.example.financeassistant.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Operation::class], version = 1, exportSchema = false)
abstract class OperationRoomDatabase : RoomDatabase() {

    abstract fun operationDao(): OperationDao

    private class OperationDatabaseCallback(
        private val scope: CoroutineScope
    ) : Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    val operationDao = database.operationDao()

                    operationDao.deleteAll()
                }
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: OperationRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): OperationRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    OperationRoomDatabase::class.java,
                    "operation_database"
                )
                    .addCallback(OperationDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

