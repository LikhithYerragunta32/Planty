package uk.ac.tees.mad.planty.data.remote.api

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
    val common_names: List<String>,
)
