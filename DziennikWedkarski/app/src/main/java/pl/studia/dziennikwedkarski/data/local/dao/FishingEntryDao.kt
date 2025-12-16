package pl.studia.dziennikwedkarski.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import pl.studia.dziennikwedkarski.data.local.entity.FishingEntryEntity
import androidx.room.Delete
import androidx.room.Update


@Dao
interface FishingEntryDao {

    @Insert
    suspend fun insert(entry: FishingEntryEntity)

    @Query("SELECT * FROM fishing_entries ORDER BY date DESC")
    suspend fun getAllEntries(): List<FishingEntryEntity>

    @Query("DELETE FROM fishing_entries")
    suspend fun deleteAll()

    @Query("SELECT * FROM fishing_entries WHERE id = :id")
    suspend fun getEntryById(id: Long): FishingEntryEntity?

    @Delete
    suspend fun delete(entry: FishingEntryEntity)

    @Update
    suspend fun update(entry: FishingEntryEntity)
}
