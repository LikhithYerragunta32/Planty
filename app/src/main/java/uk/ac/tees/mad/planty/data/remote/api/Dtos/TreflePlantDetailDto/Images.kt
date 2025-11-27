package uk.ac.tees.mad.planty.data.remote.api.Dtos.TreflePlantDetailDto

data class Images(
    val bark: List<Bark>,
    val flower: List<FlowerX>,
    val fruit: List<Fruit>,
    val habit: List<Habit>,
    val leaf: List<Leaf>,
    val other: List<Other>
)