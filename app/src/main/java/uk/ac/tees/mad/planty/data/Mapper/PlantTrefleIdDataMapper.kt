package uk.ac.tees.mad.planty.data.Mapper

import TrefleIdDto
import uk.ac.tees.mad.planty.domain.model.DomainTrefleData

fun TrefleIdDto.toDomainPlantTrefleData(): List<DomainTrefleData> {
    return data.map { plant ->
        DomainTrefleData(
            id = plant.id,
            common_name = plant.common_name,
            scientific_name = plant.scientific_name,
            image_url = plant.image_url
        )
    }
}



