package uk.ac.tees.mad.planty.data.remote.api.Dtos.TreflePlantDetailDto

data class Distribution(
    val introduced: List<String>,
    val native: List<String>
)