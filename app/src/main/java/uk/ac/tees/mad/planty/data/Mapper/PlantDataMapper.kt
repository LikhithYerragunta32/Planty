package uk.ac.tees.mad.planty.data.Mapper

import uk.ac.tees.mad.planty.data.remote.api.PlantResponse
import uk.ac.tees.mad.planty.domain.model.DomainPlantData

fun PlantResponse.toPlantInfoList(): List<DomainPlantData> {
    return result.classification.suggestions.map {
        DomainPlantData(
            plantName = it.name,
            probability = it.probability,
            commonNames = it.details.common_names,
            infoUrl = it.details.url
        )
    }
}
