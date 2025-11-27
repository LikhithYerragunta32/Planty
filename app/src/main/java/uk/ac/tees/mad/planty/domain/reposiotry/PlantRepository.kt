package uk.ac.tees.mad.planty.domain.reposiotry


import TrefleIdDto
import uk.ac.tees.mad.planty.data.remote.api.Dtos.TreflePlantDetailDto.TreflePlantDetailDto
import uk.ac.tees.mad.planty.domain.model.DomainPlantData
import uk.ac.tees.mad.planty.domain.model.DomainPlantDetail
import uk.ac.tees.mad.planty.domain.model.DomainTrefleData


interface PlantRepository  {


    suspend fun identifyPlant(imageUrl: String): Result<List<DomainPlantData>>

    suspend fun Trefle(plantName: String):  Result<List<DomainTrefleData>>

    suspend fun PlantDetailTrefle(plantId: Int  ): Result<DomainPlantDetail>



}
