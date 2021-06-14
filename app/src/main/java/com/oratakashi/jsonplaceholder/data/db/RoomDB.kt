package com.oratakashi.jsonplaceholder.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.oratakashi.jsonplaceholder.data.db.dao.PhotosDao
import com.oratakashi.jsonplaceholder.data.model.Photos

@Database(
    entities = [
        Photos::class
    ],
    version = 1
)
abstract class RoomDB : RoomDatabase() {

    abstract fun photos() : PhotosDao

    companion object {
        @Volatile
        private var instance: RoomDB? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            RoomDB::class.java,
            "JsonPlaceholder.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}