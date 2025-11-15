package uk.ac.tees.mad.planty.presentation.HIltViewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uk.ac.tees.mad.planty.domain.model.DomainPlantData
import uk.ac.tees.mad.planty.domain.model.DomainTrefleData
import uk.ac.tees.mad.planty.domain.reposiotry.PlantRepository
import uk.ac.tees.mad.planty.domain.reposiotry.usecase.PlantUseCase
import uk.ac.tees.mad.planty.domain.reposiotry.usecase.TrefleUseCase
import javax.inject.Inject

@HiltViewModel
class HomeViewmodel @Inject constructor(
    private val plantUseCase: PlantUseCase,
    private val trefleUseCase: TrefleUseCase,
    private val plantRepository: PlantRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(PlantScreenData.UiState())
    val uiState = _uiState.asStateFlow()

    private val _trefleUiState = MutableStateFlow(PlantScreenData.TrefleUiState())
    val trefleUiState = _trefleUiState.asStateFlow()
    init {


    }

//    fun getId(plantName: String) {
//        viewModelScope.launch {
//            plantRepository.Trefle(
//                plantName = plantName
//            )
//
//        }
//    }

    fun fetchPlantId(plantName: String) {
        viewModelScope.launch {
            trefleUseCase.invoke(plantName = plantName).onStart {
                _trefleUiState.update {
                    PlantScreenData.TrefleUiState(isLoading = true)

                }
                Log.d("PlantViewModel2", "Fetching plant data...")
            }
                .collect { result ->
                    result.onSuccess { data ->
                        _trefleUiState.update {
                            PlantScreenData.TrefleUiState(data = data)
                        }
                    }.onFailure { error ->
                        _trefleUiState.update {
                            PlantScreenData.TrefleUiState(error = error.message.toString())
                        }
                        Log.e("PlantViewModel2", " Error fetching plant data: ${error.message}")
                    }
                }
        }
    }

    fun fetchPlantData(image: String) {
        viewModelScope.launch {
            plantUseCase.invoke(image)
                .onStart {
                    _uiState.update { PlantScreenData.UiState(isLoading = true) }
                    Log.d("PlantViewModel", "Fetching plant data...")
                }
                .collect { result ->
                    result.onSuccess { data ->

                        _uiState.update { PlantScreenData.UiState(data = data) }

                    }.onFailure { error ->
                        _uiState.update { PlantScreenData.UiState(error = error.message.toString()) }
                        Log.e("PlantViewModel", " Error fetching plant data: ${error.message}")
                    }
                }
        }
    }

}

data object PlantScreenData {

    data class UiState(
        val isLoading: Boolean = false,
        val error: String = "",
        val data: List<DomainPlantData>? = null,

    )

    data class TrefleUiState(

        val isLoading: Boolean = false,
        val error: String = "",
        val data: List<DomainTrefleData>? = null,

    )

}

//data.forEachIndexed { index, it ->
//    Log.d(
//        "PlantViewModel",
//        """
//        🪴 Plant #${index + 1}
//        Name: ${it.plantName}
//        Probability: ${(it.probability * 100).toInt()}%
//        Common Names: ${it.commonNames?.joinToString(", ") ?: "N/A"}
//        Info URL: ${it.infoUrl ?: "No link available"}
//        ------------------------------
//        """.trimIndent()
//    )
//}