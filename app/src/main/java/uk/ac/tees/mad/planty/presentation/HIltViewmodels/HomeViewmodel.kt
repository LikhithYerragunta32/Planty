package uk.ac.tees.mad.planty.presentation.HIltViewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uk.ac.tees.mad.planty.domain.reposiotry.PlantRepository
import javax.inject.Inject

@HiltViewModel
class HomeViewmodel @Inject constructor( private val repository: PlantRepository): ViewModel() {


    init {
        
        identifyPlant(
            imageUrl = "https://xpcsvomqvzbqjkauwrre.supabase.co/storage/v1/object/public/profile_images/profile_images/bcQY8dUza6chzMEjVCg7wIkrT6r2.jpg"
        )
    }
    
    fun identifyPlant(imageUrl: String) {
        viewModelScope.launch {
            val response = repository.identifyPlant(imageUrl)
            response?.result?.classification?.suggestions?.forEach {
                Log.d("PlantID", "Plant: ${it.name}, Probability: ${it.probability}")
            } ?: Log.d("PlantID", "No result from API")
        }
    }

}