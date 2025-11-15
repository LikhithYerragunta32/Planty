package uk.ac.tees.mad.planty.data.remote.api.Dtos.PlantIdDto

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable


@Serializable
data class PlantResponse(
    val result: Result,
)

@Serializable
data class Result(
    val is_plant: IsPlant,
    val classification: Classification,
)

@Serializable
data class IsPlant(
    val binary: Boolean,
)

@Serializable
data class Classification(
    val suggestions: List<Suggestion>,
)

@Serializable
data class Suggestion(
    val name: String,
    val probability: Double,
    val details: Details,
)

@Serializable
data class Details(
    val url: String,
    @SerializedName("common_names")
    val commonNames: List<String>,
)
