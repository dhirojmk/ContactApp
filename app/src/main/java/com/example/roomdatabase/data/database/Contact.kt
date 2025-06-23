package com.example.roomdatabase.data.database
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts_table")
data class Contact (
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name: String,
    var phone: String,
    var email: String,
    var isActive: Boolean,
    var dateOfCreation: Long,
    var image: ByteArray? = null
)