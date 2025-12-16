package pl.studia.dziennikwedkarski.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import pl.studia.dziennikwedkarski.data.local.entity.PhotoEntity
import androidx.room.Delete

@Dao
interface PhotoDao {

    @Insert
    suspend fun insert(photo: PhotoEntity)

    @Query("SELECT * FROM photos WHERE entryId = :entryId")
    suspend fun getPhotosForEntry(entryId: Long): List<PhotoEntity>

    @Delete
    suspend fun delete(photo: PhotoEntity)
}