package uk.ac.tees.mad.planty.data.remote.api

import TrefleIdDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import uk.ac.tees.mad.planty.data.remote.api.Dtos.TreflePlantDetailDto.TreflePlantDetailDto

interface PlantDetailApi {

    @GET("plants/{id}")
    suspend fun getPlantDetails(
        @Path("id") id: Int,
        @Query("token") apiKey: String
    ): Response<TreflePlantDetailDto>
}
