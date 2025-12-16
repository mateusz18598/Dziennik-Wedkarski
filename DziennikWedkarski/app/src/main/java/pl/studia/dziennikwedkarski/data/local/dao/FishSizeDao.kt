package pl.studia.dziennikwedkarski.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete
import pl.studia.dziennikwedkarski.data.local.entity.FishSizeEntity

@Dao
interface FishSizeDao {

    @Insert
    suspend fun insert(size: FishSizeEntity)

    @Query("SELECT * FROM fish_sizes WHERE fishCatchId = :fishCatchId")
    suspend fun getSizesForFish(fishCatchId: Long): List<FishSizeEntity>

    @Delete
    suspend fun delete(size: FishSizeEntity)

    @Query("SELECT MAX(lengthCm) FROM fish_sizes")
    suspend fun getMaxFishSize(): Int?

    @Query("SELECT AVG(lengthCm) FROM fish_sizes")
    suspend fun getAverageFishSize(): Double?

}
