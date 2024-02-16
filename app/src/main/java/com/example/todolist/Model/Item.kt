package com.example.todolist.Model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "todo_table")
@Parcelize
data class Item(
    @PrimaryKey(autoGenerate = true)
    var id: Int= 0,
    var title : String,
    @ColumnInfo(name = "desc")
    var description : String,
    @ColumnInfo(name = "created_at")
    var createdAt:Long= System.currentTimeMillis()
):Parcelable
