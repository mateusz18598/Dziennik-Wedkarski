package pl.studia.dziennikwedkarski.data.repository

import pl.studia.dziennikwedkarski.data.local.dao.FishingEntryDao
import pl.studia.dziennikwedkarski.data.local.dao.PhotoDao
import pl.studia.dziennikwedkarski.data.local.entity.FishingEntryEntity
import pl.studia.dziennikwedkarski.data.local.entity.PhotoEntity
import pl.studia.dziennikwedkarski.data.local.entity.FishCatchEntity
import pl.studia.dziennikwedkarski.data.local.dao.FishCatchDao
import pl.studia.dziennikwedkarski.data.local.entity.FishSizeEntity
import pl.studia.dziennikwedkarski.data.local.dao.FishSizeDao

class FishingEntryRepository(
    private val entryDao: FishingEntryDao,
    private val photoDao: PhotoDao,
    private val fishCatchDao: FishCatchDao,
    private val fishSizeDao: FishSizeDao
) {

    // --- ENTRIES ---

    suspend fun getEntries(): List<FishingEntryEntity> =
        entryDao.getAllEntries()

    suspend fun getEntryById(id: Long): FishingEntryEntity? =
        entryDao.getEntryById(id)

    suspend fun addEntry(entry: FishingEntryEntity) =
        entryDao.insert(entry)

    suspend fun updateEntry(entry: FishingEntryEntity) =
        entryDao.update(entry)

    suspend fun deleteEntry(entry: FishingEntryEntity) =
        entryDao.delete(entry)

    // --- PHOTOS ---

    suspend fun getPhotos(entryId: Long): List<PhotoEntity> =
        photoDao.getPhotosForEntry(entryId)

    suspend fun addPhoto(entryId: Long, uri: String) =
        photoDao.insert(
            PhotoEntity(
                entryId = entryId,
                uri = uri
            )
        )
    suspend fun deletePhoto(photo: PhotoEntity) {
        photoDao.delete(photo)
    }

    // --- FISH ---

    suspend fun getFish(entryId: Long): List<FishCatchEntity> =
        fishCatchDao.getFishForEntry(entryId)

    suspend fun addFish(entryId: Long, species: String, count: Int) =
        fishCatchDao.insert(
            FishCatchEntity(
                entryId = entryId,
                species = species,
                count = count
            )
        )

    suspend fun deleteFish(fish: FishCatchEntity) =
        fishCatchDao.delete(fish)

    // --- FISH SIZES ---

    suspend fun getFishSizes(fishCatchId: Long): List<FishSizeEntity> =
        fishSizeDao.getSizesForFish(fishCatchId)

    suspend fun addFishSize(fishCatchId: Long, lengthCm: Int) =
        fishSizeDao.insert(
            FishSizeEntity(
                fishCatchId = fishCatchId,
                lengthCm = lengthCm
            )
        )

    suspend fun deleteFishSize(size: FishSizeEntity) =
        fishSizeDao.delete(size)

    // --- STATISTICS ---

    suspend fun getMaxFishSize(): Int =
        fishSizeDao.getMaxFishSize() ?: 0

    suspend fun getAverageFishSize(): Double =
        fishSizeDao.getAverageFishSize() ?: 0.0

    suspend fun getTotalFishCount(): Int =
        fishCatchDao.getTotalFishCount() ?: 0
}
