package com.example.todolist.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.todolist.Model.Item
@Dao
interface TodoDao {
    @Insert
    fun insertItem(item: Item)

    @Query("select * from todo_table")
    fun getAllItems():MutableList<Item>

    @Delete
    fun deleteItem(item: Item)

    @Update
    fun updateItem(item: Item)

}