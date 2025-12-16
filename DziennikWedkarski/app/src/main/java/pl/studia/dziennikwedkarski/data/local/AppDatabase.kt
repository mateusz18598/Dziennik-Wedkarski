package pl.studia.dziennikwedkarski.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import pl.studia.dziennikwedkarski.data.local.dao.FishingEntryDao
import pl.studia.dziennikwedkarski.data.local.dao.PhotoDao
import pl.studia.dziennikwedkarski.data.local.dao.FishCatchDao
import pl.studia.dziennikwedkarski.data.local.dao.FishSizeDao
import pl.studia.dziennikwedkarski.data.local.entity.FishingEntryEntity
import pl.studia.dziennikwedkarski.data.local.entity.PhotoEntity
import pl.studia.dziennikwedkarski.data.local.entity.FishCatchEntity
import pl.studia.dziennikwedkarski.data.local.entity.FishSizeEntity

@Database(
    entities = [
        FishingEntryEntity::class,
        PhotoEntity::class,
        FishCatchEntity::class,
        FishSizeEntity::class
    ],
    version = 4,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun fishingEntryDao(): FishingEntryDao
    abstract fun photoDao(): PhotoDao
    abstract fun fishCatchDao(): FishCatchDao
    abstract fun fishSizeDao(): FishSizeDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "fishing_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
