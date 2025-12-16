package pl.studia.dziennikwedkarski.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete
import pl.studia.dziennikwedkarski.data.local.entity.FishCatchEntity

@Dao
interface FishCatchDao {

    @Insert
    suspend fun insert(fish: FishCatchEntity)

    @Query("SELECT * FROM fish_catches WHERE entryId = :entryId")
    suspend fun getFishForEntry(entryId: Long): List<FishCatchEntity>

    @Delete
    suspend fun delete(fish: FishCatchEntity)

    @Query("SELECT SUM(count) FROM fish_catches")
    suspend fun getTotalFishCount(): Int?

}
