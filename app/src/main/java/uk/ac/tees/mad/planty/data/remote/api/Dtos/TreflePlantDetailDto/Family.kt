package uk.ac.tees.mad.planty.data.remote.api.Dtos.TreflePlantDetailDto

data class Family(
    val common_name: Any,
    val id: Int,
    val links: Links,
    val name: String,
    val slug: String
)