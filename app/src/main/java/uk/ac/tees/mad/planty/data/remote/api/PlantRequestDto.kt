package uk.ac.tees.mad.planty.data.remote.api



data class PlantResponse(
    val result: Result
)

data class Result(
    val is_plant: IsPlant,
    val classification: Classification
)

data class IsPlant(
    val binary: Boolean
)

data class Classification(
    val suggestions: List<Suggestion>
)

data class Suggestion(
    val name: String,
    val probability: Double,
    val details: Details
)

data class Details(
    val url: String,
    val common_names: List<String>
)
