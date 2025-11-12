package uk.ac.tees.mad.planty.data.repoImpl

import android.util.Log
import com.google.gson.Gson
import uk.ac.tees.mad.planty.data.Mapper.toPlantInfoList
import uk.ac.tees.mad.planty.data.remote.api.PlantApi
import uk.ac.tees.mad.planty.data.remote.api.PlantRequest
import uk.ac.tees.mad.planty.data.remote.api.PlantResponse
import uk.ac.tees.mad.planty.domain.model.DomainPlantData

import uk.ac.tees.mad.planty.domain.reposiotry.PlantRepository

class PlantRepositoryImpl(private val api: PlantApi) : PlantRepository {


    private val API_KEY = "ENZF56UDT2C8iUX2ueep5OoNTDILmnsGLZRbOWtlYPo2K1hRLJ"

    override suspend fun identifyPlant(imageUrl: String): Result<List<DomainPlantData>> {
        return try {
            val request = PlantRequest(
                images = listOf(imageUrl),
                api_key = API_KEY,

            )

            val response = api.identifyPlant(request)

            if (response.isSuccessful) {
                response.body()?.let { plantResponse ->

                    val json = Gson().toJson(plantResponse)
                    Log.d("PlantRepository", "Full PlantResponse: $json")

                    Result.success(plantResponse.toPlantInfoList())
                } ?: Result.failure(Exception("Empty response body"))
            } else {
                Result.failure(Exception("API error: ${response.code()} ${response.message()}"))
            }

        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }


}