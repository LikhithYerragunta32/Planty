package uk.ac.tees.mad.planty.data.remote.api.Dtos.TreflePlantDetailDto

data class Growth(
    val atmospheric_humidity: Any,
    val bloom_months: Any,
    val days_to_harvest: Any,
    val description: Any,
    val fruit_months: Any,
    val growth_months: Any,
    val light: Any,
    val maximum_precipitation: MaximumPrecipitation,
    val maximum_temperature: MaximumTemperature,
    val minimum_precipitation: MinimumPrecipitation,
    val minimum_root_depth: MinimumRootDepth,
    val minimum_temperature: MinimumTemperature,
    val ph_maximum: Any,
    val ph_minimum: Any,
    val row_spacing: RowSpacing,
    val soil_humidity: Any,
    val soil_nutriments: Any,
    val soil_salinity: Any,
    val soil_texture: Any,
    val sowing: Any,
    val spread: Spread
)