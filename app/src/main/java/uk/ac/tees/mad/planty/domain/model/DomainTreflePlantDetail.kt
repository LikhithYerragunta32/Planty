package uk.ac.tees.mad.planty.domain.model

import android.util.Log
import uk.ac.tees.mad.planty.data.remote.api.Dtos.TreflePlantDetailDto.Family
import uk.ac.tees.mad.planty.data.remote.api.Dtos.TreflePlantDetailDto.TreflePlantDetailDto


data class DomainPlantDetail(
    val id: Int,
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


fun TreflePlantDetailDto.toDomainPlantDetail(): DomainPlantDetail {

    val plant = this.data
    Log.d("PlantData", plant.toString())
    return DomainPlantDetail(
        id = plant.id,
        commonName = plant.common_name,
        familyName = plant.family.name,
        scientificName = plant.scientific_name,
        species = plant.genus.name,
        imageUrl = plant.image_url,
        bibliography = plant.bibliography,
        family = plant.family.name,
        genus = plant.genus.name,
        vegetable = plant.vegetable,
        observations = plant.observations
    )


}



