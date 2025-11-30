package uk.ac.tees.mad.planty.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
@Dao
interface PlantDao {

    @Upsert()
    suspend fun insert(plant: PlantEntity)

    @Query("DELETE FROM plant_table WHERE plantId = :id")
    suspend fun deleteById(id: String)

    @Query("SELECT * FROM plant_table WHERE plantId IN (:ids) ORDER BY commonName ASC")
    fun getPlantsByIds(ids: List<String>): Flow<List<PlantEntity>>


}