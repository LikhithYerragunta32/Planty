data class TrefleIdDto(
    val data: List<Plant>
)

data class Plant(
    val id: Int,
    val common_name: String?,
    val scientific_name: String?,
    val image_url: String?
)
