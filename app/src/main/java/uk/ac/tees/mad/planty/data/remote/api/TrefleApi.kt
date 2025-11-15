package uk.ac.tees.mad.planty.data.remote.api



import TrefleIdDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TrefleApi {

    @GET("plants/search")
    suspend fun searchPlant(
        @Query("token") apiKey: String,
        @Query("q") plantName: String
    ): Response<TrefleIdDto>
}