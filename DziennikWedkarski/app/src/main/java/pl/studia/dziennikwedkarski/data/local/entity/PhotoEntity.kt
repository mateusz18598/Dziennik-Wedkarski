package pl.studia.dziennikwedkarski.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photos")
data class PhotoEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val entryId: Long,   // ID wpisu
    val uri: String     // sciezka do zdjecia
)
