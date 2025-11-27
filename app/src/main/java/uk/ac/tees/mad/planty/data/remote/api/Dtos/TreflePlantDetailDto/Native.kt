package uk.ac.tees.mad.planty.data.remote.api.Dtos.TreflePlantDetailDto

data class Native(
    val id: Int,
    val links: LinksXXX,
    val name: String,
    val slug: String,
    val species_count: Int,
    val tdwg_code: String,
    val tdwg_level: Int
)