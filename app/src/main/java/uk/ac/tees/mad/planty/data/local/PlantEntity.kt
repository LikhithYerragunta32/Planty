package uk.ac.tees.mad.planty.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity("plant_table")
data class PlantEntity(

    @PrimaryKey
    val plantId: String,
    val commonName: String,
    val familyName: String,
    val scientificName: String,
    val species: String,
    val imageUrl: String,
    val family: String,
    val genus: String,
    val bibliography: String,
    val vegetable: Boolean,
    val observations: String,

)