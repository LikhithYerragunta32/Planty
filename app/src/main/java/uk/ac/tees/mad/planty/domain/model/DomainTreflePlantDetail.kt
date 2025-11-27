package uk.ac.tees.mad.planty.domain.model

import uk.ac.tees.mad.planty.data.remote.api.Dtos.TreflePlantDetailDto.TreflePlantDetailDto


data class DomainPlantDetail(
    val id: Int,
    val commonName: String?,
    val scientificName: String?,
    val imageUrl: String?,
    val family: String?,
    val genus: String?,
    val duration: String?,
    val growthLight: Int?,
    val growthSoilTexture: String?,
    val growthAtmosphericHumidity: Int?,
    val bibliography: String?,
)


fun TreflePlantDetailDto.toDomainPlantDetail(): DomainPlantDetail {
    val plant = this.data

    return DomainPlantDetail(
        id = plant.id,
        commonName = plant.common_name,
        scientificName = plant.scientific_name,
        imageUrl = plant.image_url,
        family = plant.family?.name,
        genus = plant.genus?.name,
        duration = plant.main_species?.duration as String?,
        growthLight = plant.main_species?.growth?.light as Int?,
        growthSoilTexture = plant.main_species?.growth?.soil_texture as String?,
        growthAtmosphericHumidity = plant.main_species?.growth?.atmospheric_humidity as Int?,
        bibliography = plant.bibliography
    )
}



