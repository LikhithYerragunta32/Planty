package uk.ac.tees.mad.planty.domain.reposiotry

import uk.ac.tees.mad.planty.data.remote.api.PlantApi
import uk.ac.tees.mad.planty.data.remote.api.PlantRequest
import uk.ac.tees.mad.planty.data.remote.api.PlantResponse
import javax.inject.Inject


interface PlantRepository  {


    suspend fun identifyPlant(imageUrl: String): Result<PlantResponse>


}
