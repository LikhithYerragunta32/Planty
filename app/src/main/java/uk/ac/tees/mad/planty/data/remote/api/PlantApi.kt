package uk.ac.tees.mad.planty.data.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import uk.ac.tees.mad.planty.data.remote.api.Dtos.PlantIdDto.PlantResponse


data class PlantRequest(
    val api_key: String,
    val images: List<String>,

)


interface PlantApi {

    @Headers("Content-Type: application/json")
    @POST("v3/identification")
    suspend fun identifyPlant(
        @Body request: PlantRequest
    ): Response<PlantResponse>
}