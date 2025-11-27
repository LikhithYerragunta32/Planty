package uk.ac.tees.mad.planty.data.remote.api.Dtos.TreflePlantDetailDto

data class Distributions(
    val introduced: List<Introduced>,
    val native: List<Native>
)