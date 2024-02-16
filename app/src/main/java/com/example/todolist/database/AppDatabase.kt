package com.example.todolist.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todolist.Dao.TodoDao
import com.example.todolist.Model.Item

@Database(entities = [Item::class], version = 3)
abstract class AppDatabase: RoomDatabase() {

    // abstract method
    abstract fun todoDao():TodoDao
    companion object {

        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(this){
                    INSTANCE =
                        Room.databaseBuilder(context, AppDatabase::class.java, "todo.db").allowMainThreadQueries().build()
                }
            }
            return INSTANCE
        }

    }

}
