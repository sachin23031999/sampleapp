package com.example.sample.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sample.models.Details

@Dao
interface DetailsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDetail(details: List<Details>)

    @Query("SELECT * FROM Details")
    fun getDetailList(): LiveData<List<Details>>
}