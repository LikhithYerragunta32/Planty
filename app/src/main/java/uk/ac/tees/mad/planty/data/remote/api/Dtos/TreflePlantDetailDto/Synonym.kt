package uk.ac.tees.mad.planty.data.remote.api.Dtos.TreflePlantDetailDto

data class Synonym(
    val author: String,
    val id: Int,
    val name: String,
    val sources: List<SourceXX>
)