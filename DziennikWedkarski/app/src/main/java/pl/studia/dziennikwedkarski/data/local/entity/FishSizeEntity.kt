package pl.studia.dziennikwedkarski.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fish_sizes")
data class FishSizeEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val fishCatchId: Long,   // ID FishCatchEntity
    val lengthCm: Int        // długość w cm
)
