package uk.ac.tees.mad.planty.data.repoImpl

import Plant
import TrefleIdDto
import android.R.attr.data
import android.util.Log
import com.google.gson.Gson
import uk.ac.tees.mad.planty.data.Mapper.toDomainPlantTrefleData

import uk.ac.tees.mad.planty.data.Mapper.toPlantInfoList
import uk.ac.tees.mad.planty.data.remote.api.PlantApi
import uk.ac.tees.mad.planty.data.remote.api.PlantRequest
import uk.ac.tees.mad.planty.data.remote.api.TrefleApi
import uk.ac.tees.mad.planty.domain.model.DomainPlantData
import uk.ac.tees.mad.planty.domain.model.DomainTrefleData

import uk.ac.tees.mad.planty.domain.reposiotry.PlantRepository
import uk.ac.tees.mad.planty.presentation.AuthScreens.LogInScreen

class PlantRepositoryImpl(private val api: PlantApi, private val trefleApi: TrefleApi) :
    PlantRepository {


//    private val API_KEY = "ENZF56UDT2C8iUX2ueep5OoNTDILmnsGLZRbOWtlYPo2K1hRLJ"

    //    private val API_KEY = "uSEUmJMRC8YZ4viogZHZytB6lES1OOGprjf9nZCPNh8VVNWsQn"
    private val API_KEY = "hK3In0PuF5gyYyb7cCAld9opjjtmv6kJVuBSn0eP82L4FeBr4c"

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


    override suspend fun Trefle(plantName: String): Result<List<DomainTrefleData>> {
        return try {
            val response = trefleApi.searchPlant(
                apiKey = "usr-wxVNIpI_JSulNq0RAdDxUavms5b4hBIH9tv7GUvSQPc", plantName = plantName
            )

            if (response.isSuccessful) {
                response.body()?.let {

                    Log.d("taggg", "Mapped domain data: ${it.data}")

                    Result.success(it.toDomainPlantTrefleData())


                } ?: Result.failure(Exception("Empty response body"))
            } else {
                Result.failure(Exception("API error: ${response.code()} ${response.message()}"))
            }

        } catch (e: Exception) {
            Log.e("taggg", "Error fetching data: ${e.message}", e)
            Result.failure(e)
        }
    }

}




