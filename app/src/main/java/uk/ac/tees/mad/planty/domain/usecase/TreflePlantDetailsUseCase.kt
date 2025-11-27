package uk.ac.tees.mad.planty.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uk.ac.tees.mad.planty.domain.reposiotry.PlantRepository

class TreflePlantDetailsUseCase(private val plantRepository: PlantRepository) {


    operator fun invoke(plantName: Int) = flow {
        emit(
            value = plantRepository.PlantDetailTrefle(plantName)
        )
    }.catch { error ->

        emit(Result.failure(error))

    }.flowOn(Dispatchers.IO)
}