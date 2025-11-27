package uk.ac.tees.mad.planty.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uk.ac.tees.mad.planty.domain.reposiotry.PlantRepository
import java.lang.reflect.Constructor
import javax.inject.Inject

class TrefleUseCase (private val plantRepository: PlantRepository){


    operator fun invoke(plantName: String) = flow {
        emit(
            value = plantRepository.Trefle(plantName)
        )
    }.catch { error ->

        emit(Result.failure(error))

    }.flowOn(Dispatchers.IO)

}