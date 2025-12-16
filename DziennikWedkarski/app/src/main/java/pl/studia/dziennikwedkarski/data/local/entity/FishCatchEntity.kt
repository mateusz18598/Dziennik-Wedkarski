package pl.studia.dziennikwedkarski.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fish_catches")
data class FishCatchEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val entryId: Long,      // ID wpisu
    val species: String,    // np. Szczupak
    val count: Int          // ilość sztuk
)
