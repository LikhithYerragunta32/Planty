package uk.ac.tees.mad.planty.presentation.HIltViewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import uk.ac.tees.mad.planty.data.local.PlantDao
import uk.ac.tees.mad.planty.data.local.PlantEntity
import uk.ac.tees.mad.planty.domain.model.DomainPlantData
import uk.ac.tees.mad.planty.domain.model.DomainPlantDetail
import uk.ac.tees.mad.planty.domain.model.DomainTrefleData
import uk.ac.tees.mad.planty.domain.reposiotry.PlantRepository
import uk.ac.tees.mad.planty.domain.usecase.PlantUseCase
import uk.ac.tees.mad.planty.domain.usecase.TreflePlantDetailsUseCase
import uk.ac.tees.mad.planty.domain.usecase.TrefleUseCase
import javax.inject.Inject
import kotlin.jvm.java

@HiltViewModel
class HomeViewmodel @Inject constructor(

    private val plantUseCase: PlantUseCase,
    private val trefleUseCase: TrefleUseCase,
    private val treflePlantDetailsUseCase: TreflePlantDetailsUseCase,
    private val plantRepository: PlantRepository,
    private val plantDao: PlantDao,


    ) : ViewModel() {


    private val _uiState = MutableStateFlow(PlantScreenData.UiState())
    val uiState = _uiState.asStateFlow()

    private val _trefleUiState = MutableStateFlow(PlantScreenData.TrefleUiState())
    val trefleUiState = _trefleUiState.asStateFlow()

    private val _plantDetailsUiStates = MutableStateFlow(PlantScreenData.PlantDetailUiState())
    val plantDetailsUiStates = _plantDetailsUiStates.asStateFlow()



    fun getId(plantName: String) {
        viewModelScope.launch {
            plantRepository.Trefle(
                plantName = plantName
            )

        }
    }

    fun fetchPlantId(plantName: String) {
        viewModelScope.launch {
            trefleUseCase.invoke(plantName = plantName).onStart {
                _trefleUiState.update {
                    PlantScreenData.TrefleUiState(isLoading = true)

                }
                Log.d("PlantViewModel2", "Fetching plant data...")
            }.collect { result ->
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
            plantUseCase.invoke(image).onStart {
                    _uiState.update { PlantScreenData.UiState(isLoading = true) }
                    Log.d("PlantViewModel", "Fetching plant data...")
                }.collect { result ->
                    result.onSuccess { data ->

                        _uiState.update { PlantScreenData.UiState(data = data) }

                    }.onFailure { error ->
                        _uiState.update { PlantScreenData.UiState(error = error.message.toString()) }
                        Log.e("PlantViewModel", " Error fetching plant data: ${error.message}")
                    }
                }
        }
    }

    fun fetPlantDetail(plantId: Int) {
        viewModelScope.launch {
            treflePlantDetailsUseCase.invoke(plantId).onStart {
                    _plantDetailsUiStates.update {
                        PlantScreenData.PlantDetailUiState(isLoading = true)
                    }
                }.collect { result ->
                    result.onSuccess { data ->

                        _plantDetailsUiStates.update { PlantScreenData.PlantDetailUiState(data = data) }
                    }.onFailure { error ->
                        _plantDetailsUiStates.update { PlantScreenData.PlantDetailUiState(error = error.message.toString()) }
                        Log.e("PlantViewModel", " Error fetching plant data: ${error.message}")
                    }
                }
        }
    }


    val db = FirebaseFirestore.getInstance()
    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    val firestore = FirebaseFirestore.getInstance()

    fun addPlant(
        plantId: String,
        commonName: String,
        familyName: String,
        scientificName: String,
        species: String,
        imageUrl: String,
        family: String,
        genus: String,
        bibliography: String,
        observations: String,
        vegetable: Boolean,

        onResult: (
            Boolean,
            String?,
        ) -> Unit,


        ) {
        val uid = auth.currentUser?.uid ?: return onResult(false, "User not logged in")

        val userRef = firestore.collection("user").document(uid)

        userRef.get().addOnSuccessListener { doc ->
                val savedPlants = doc.get("savedPlant") as? List<String> ?: emptyList()

                if (savedPlants.contains(plantId)) {
                    onResult(false, "This plant is already in your saved list.")
                } else {
                    userRef.update("savedPlant", FieldValue.arrayUnion(plantId))
                        .addOnSuccessListener {
                            onResult(true, "Plant added successfully.")
                        }.addOnFailureListener { e ->
                            onResult(false, e.message)
                        }
                }
            }.addOnFailureListener { e ->
                onResult(false, e.message)
            }
        viewModelScope.launch {
            plantDao.insert(
                PlantEntity(
                    plantId = plantId,
                    commonName = commonName,
                    familyName = familyName,
                    scientificName = scientificName,
                    species = species,
                    imageUrl = imageUrl,
                    family = family,
                    genus = genus,
                    bibliography = bibliography,
                    vegetable = vegetable,
                    observations = observations
                )
            )
        }
    }


    private val _isLoading = MutableStateFlow(false)
    var isLoading: StateFlow<Boolean> = _isLoading

    private val _savedPlant = MutableStateFlow<List<PlantEntity>>(emptyList())

    val savedPlant: StateFlow<List<PlantEntity>> = _savedPlant


    fun getSavedPlant() {
        val uid = auth.currentUser?.uid ?: return

        viewModelScope.launch {

            _isLoading.value = true

            val snapshot = firestore.collection("user").document(uid).get().await()

            val savedPlant = snapshot.get("savedPlant") as? List<String> ?: emptyList()


            plantDao.getPlantsByIds(savedPlant).collect { plantData ->

                _savedPlant.value = plantData

            }


        }
        _isLoading.value = false
    }

    fun removeCity(plant: String, onResult: (Boolean, String?) -> Unit) {
        val uid = auth.currentUser?.uid ?: return onResult(false, "User not logged in")
        val userRef = firestore.collection("user").document(uid)

        userRef.get().addOnSuccessListener { doc ->
            val likedCities = doc.get("savedPlant") as? List<String> ?: emptyList()

            if (!likedCities.contains(plant)) {
                onResult(false, "Plant not found in your list")
            } else {
                userRef.update("savedPlant", FieldValue.arrayRemove(plant))
                    .addOnSuccessListener {
                        onResult(true, "Plant removed successfully")
                    }.addOnFailureListener { e ->
                        onResult(false, e.message)
                    }
            }
        }.addOnFailureListener { e ->
            onResult(false, e.message)
        }
    }



    private val _currentUserData = MutableStateFlow(GetUserInfo())
    val currentUserData: StateFlow<GetUserInfo> = _currentUserData

    fun fetchCurrentDonerData() {
        auth.currentUser?.uid?.let { userId ->

            db.collection("user").document(userId).addSnapshotListener { snapshot, e ->

                if (e != null) {

                    return@addSnapshotListener
                }

                if (snapshot != null && snapshot.exists()) {
                    val data = snapshot.toObject(GetUserInfo::class.java)
                    data?.let {
                        _currentUserData.value = it
                        Log.d("Firestore","$it")
                    }
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

    data class PlantDetailUiState(
        val isLoading: Boolean = false,
        val error: String = "",
        val data: DomainPlantDetail? = null,
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