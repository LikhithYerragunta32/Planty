package uk.ac.tees.mad.planty.data.remote.api.Dtos.TreflePlantDetailDto

data class Data(
    val author: String,
    val bibliography: String,
    val common_name: String,
    val family: Family,
    val family_common_name: Any,
    val forms: List<Any?>,
    val genus: Genus,
    val genus_id: Int,
    val hybrids: List<Any?>,
    val id: Int,
    val image_url: String,
    val links: LinksXX,
    val main_species: MainSpecies,
    val main_species_id: Int,
    val observations: String,
    val scientific_name: String,
    val slug: String,
    val sources: List<SourceXX>,
    val species: List<Specy>,
    val subspecies: List<Any?>,
    val subvarieties: List<Any?>,
    val varieties: List<Variety>,
    val vegetable: Boolean,
    val year: Int
)