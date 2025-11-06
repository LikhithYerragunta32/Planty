package uk.ac.tees.mad.planty.data.repoImpl

import uk.ac.tees.mad.planty.data.remote.api.PlantApi
import uk.ac.tees.mad.planty.data.remote.api.PlantRequest
import uk.ac.tees.mad.planty.data.remote.api.PlantResponse

import uk.ac.tees.mad.planty.domain.reposiotry.PlantRepository

class PlantRepositoryImpl(private val api: PlantApi) : PlantRepository {


    private val API_KEY = "ENZF56UDT2C8iUX2ueep5OoNTDILmnsGLZRbOWtlYPo2K1hRLJ"

    override suspend fun identifyPlant(imageUrl: String): Result<PlantResponse> {
        return try {
            val request = PlantRequest(
                images = listOf(imageUrl),
                api_key = API_KEY
            )

            val response = api.identifyPlant(request)

            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
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