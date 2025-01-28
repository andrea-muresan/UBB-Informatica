package com.example.bookstoreandroid.todo.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "books")
data class Book(
    @PrimaryKey
    val id: String = "",
    val title: String = "",
    val author: String = "",
    val publicationDate: String = "",
//    @SerializedName("isAvailable")
//    val isAvailableInt: Int = 0,
    val isAvailable: Int = 0,
//    @Transient
//    val isAvailable : Boolean = isAvailableInt == 1,
    val price: Double = 0.0,
    val dirty: Boolean? = false,
    val photo: String = "",
    val lat: Double = 0.0,
    val lng: Double = 0.0
)