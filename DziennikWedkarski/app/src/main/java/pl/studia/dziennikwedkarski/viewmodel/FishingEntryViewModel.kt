package pl.studia.dziennikwedkarski.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pl.studia.dziennikwedkarski.data.local.AppDatabase
import pl.studia.dziennikwedkarski.data.local.entity.FishingEntryEntity
import pl.studia.dziennikwedkarski.data.repository.FishingEntryRepository
import pl.studia.dziennikwedkarski.data.local.dao.PhotoDao
import pl.studia.dziennikwedkarski.data.local.entity.PhotoEntity
import pl.studia.dziennikwedkarski.data.local.entity.FishCatchEntity
import pl.studia.dziennikwedkarski.data.local.dao.FishCatchDao
import pl.studia.dziennikwedkarski.data.local.entity.FishSizeEntity
import pl.studia.dziennikwedkarski.data.local.dao.FishSizeDao

class FishingEntryViewModel(application: Application) :
    AndroidViewModel(application) {

    private val _fish = MutableStateFlow<List<FishCatchEntity>>(emptyList())
    val fish: StateFlow<List<FishCatchEntity>> = _fish
    private val _photos = MutableStateFlow<List<PhotoEntity>>(emptyList())
    val photos: StateFlow<List<PhotoEntity>> = _photos
    private val _selectedEntry = MutableStateFlow<FishingEntryEntity?>(null)
    val selectedEntry: StateFlow<FishingEntryEntity?> = _selectedEntry

    private val repository: FishingEntryRepository

    private val _entries = MutableStateFlow<List<FishingEntryEntity>>(emptyList())

    private val _fishSizes = MutableStateFlow<List<FishSizeEntity>>(emptyList())
    val fishSizes: StateFlow<List<FishSizeEntity>> = _fishSizes

    val entries: StateFlow<List<FishingEntryEntity>> = _entries

    private val _maxFishSize = MutableStateFlow(0)
    val maxFishSize: StateFlow<Int> = _maxFishSize

    private val _avgFishSize = MutableStateFlow(0.0)
    val avgFishSize: StateFlow<Double> = _avgFishSize

    private val _totalFishCount = MutableStateFlow(0)
    val totalFishCount: StateFlow<Int> = _totalFishCount

    init {
        val database = AppDatabase.getDatabase(application)

        val entryDao = database.fishingEntryDao()
        val photoDao = database.photoDao()
        val fishCatchDao = database.fishCatchDao()
        val fishSizeDao = database.fishSizeDao()

        repository = FishingEntryRepository(
            entryDao = entryDao,
            photoDao = photoDao,
            fishCatchDao = fishCatchDao,
            fishSizeDao = fishSizeDao
        )


        loadEntries()
    }


    fun loadEntries() {
        viewModelScope.launch {
            _entries.value = repository.getEntries()
        }
    }

    fun addEntry(entry: FishingEntryEntity) {
        viewModelScope.launch {
            repository.addEntry(entry)
            loadEntries()
        }
    }

    fun loadEntry(id: Long) {
        viewModelScope.launch {
            _selectedEntry.value = repository.getEntryById(id)
        }
    }

    fun deleteEntry(entry: FishingEntryEntity) {
        viewModelScope.launch {
            repository.deleteEntry(entry)
            loadEntries()
        }
    }

    fun updateEntry(entry: FishingEntryEntity) {
        viewModelScope.launch {
            repository.updateEntry(entry)
            loadEntries()
        }
    }
    fun loadPhotos(entryId: Long) {
        viewModelScope.launch {
            _photos.value = repository.getPhotos(entryId)
        }
    }

    fun addPhoto(entryId: Long, uri: String) {
        viewModelScope.launch {
            repository.addPhoto(entryId, uri)
            loadPhotos(entryId)
        }
    }

    fun deletePhoto(photo: PhotoEntity) {
        viewModelScope.launch {
            repository.deletePhoto(photo)
            loadPhotos(photo.entryId)
        }
    }

    fun loadFish(entryId: Long) {
        viewModelScope.launch {
            _fish.value = repository.getFish(entryId)
        }
    }

    fun addFish(entryId: Long, species: String, count: Int) {
        viewModelScope.launch {
            repository.addFish(entryId, species, count)
            loadFish(entryId)
        }
    }

    fun deleteFish(fish: FishCatchEntity) {
        viewModelScope.launch {
            repository.deleteFish(fish)
            loadFish(fish.entryId)
        }
    }

    fun loadFishSizes(fishCatchId: Long) {
        viewModelScope.launch {
            _fishSizes.value = repository.getFishSizes(fishCatchId)
        }
    }

    fun addFishSize(fishCatchId: Long, lengthCm: Int) {
        viewModelScope.launch {
            repository.addFishSize(fishCatchId, lengthCm)
            loadFishSizes(fishCatchId)
        }
    }

    fun deleteFishSize(size: FishSizeEntity) {
        viewModelScope.launch {
            repository.deleteFishSize(size)
            loadFishSizes(size.fishCatchId)
        }
    }

    fun loadStatistics() {
        viewModelScope.launch {
            _maxFishSize.value = repository.getMaxFishSize()
            _avgFishSize.value = repository.getAverageFishSize()
            _totalFishCount.value = repository.getTotalFishCount()
        }
    }


}
