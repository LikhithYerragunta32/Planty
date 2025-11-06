package uk.ac.tees.mad.planty.domain.reposiotry.usecase

import com.google.android.play.integrity.internal.q
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uk.ac.tees.mad.planty.domain.reposiotry.PlantRepository

class PlantUseCase( private val plantRepository: PlantRepository) {


    operator fun invoke(image : String) = flow {
        emit(
            value = plantRepository.identifyPlant(image)
        )
    }.catch { error ->

        emit(Result.failure(error))

    }.flowOn(Dispatchers.IO)


}