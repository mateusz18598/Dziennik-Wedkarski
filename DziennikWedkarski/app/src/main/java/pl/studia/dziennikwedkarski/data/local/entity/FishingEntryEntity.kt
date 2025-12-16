package pl.studia.dziennikwedkarski.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "fishing_entries")
data class FishingEntryEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val date: String,          // data połowu
    val temperature: Int,      // temperatura
    val weather: String,       // pogoda
    val pressure: Int,         // ciśnienie
    val location: String,      // łowisko
    val fishingType: String,   // spinning, feeder itd.
    val durationHours: Int,    // czas łowienia
    val notes: String          // notatki
)
