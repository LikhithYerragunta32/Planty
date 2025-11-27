package uk.ac.tees.mad.planty.data.remote.api.Dtos.TreflePlantDetailDto

data class Specy(
    val author: String,
    val bibliography: String,
    val common_name: String,
    val family: String,
    val family_common_name: Any,
    val genus: String,
    val genus_id: Int,
    val id: Int,
    val image_url: String,
    val links: LinksXXXXX,
    val rank: String,
    val scientific_name: String,
    val slug: String,
    val status: String,
    val synonyms: List<String>,
    val year: Int
)