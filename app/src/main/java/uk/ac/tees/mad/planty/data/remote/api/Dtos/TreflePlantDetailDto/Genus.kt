package uk.ac.tees.mad.planty.data.remote.api.Dtos.TreflePlantDetailDto

data class Genus(
    val id: Int,
    val links: LinksX,
    val name: String,
    val slug: String
)