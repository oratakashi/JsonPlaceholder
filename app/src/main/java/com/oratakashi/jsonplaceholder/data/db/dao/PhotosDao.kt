package com.oratakashi.jsonplaceholder.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.oratakashi.jsonplaceholder.data.model.Photos
import io.reactivex.Single

@Dao
interface PhotosDao {
    @Query("Select * from photos")
    fun getAll() : Single<List<Photos>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addAll(data: List<Photos>) : Single<List<Long>>
}