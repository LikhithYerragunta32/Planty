package uk.ac.tees.mad.planty.domain.model

data class DomainPlantData(
    val plantName: String,
    val probability: Double,
    val commonNames: List<String>?,
    val infoUrl: String?
)