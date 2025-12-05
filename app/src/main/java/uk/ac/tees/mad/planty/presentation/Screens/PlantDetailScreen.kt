@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.plantapp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.google.android.play.integrity.internal.l
import kotlinx.coroutines.launch
import uk.ac.tees.mad.planty.presentation.HIltViewmodels.HomeViewmodel


@Composable
fun PlantDetailScreen(
    modifier: Modifier = Modifier,
    homeViewmodel: HomeViewmodel,
    plantId: Int,

    ) {
    LaunchedEffect(Unit) {
        homeViewmodel.fetPlantDetail(plantId)
    }
    val context = LocalContext.current
    val plantDetailsUiStates = homeViewmodel.plantDetailsUiStates.collectAsState().value
    val plant = plantDetailsUiStates.data

    val bgColor = Color(0xFFE8F5E9)
    val textColor = Color(0xFF2E7D32)
    var isLiked by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
        TopAppBar(
            title = {
            Text(
                text = plant?.commonName ?: "Plant Details",
                color = Color.White,
                style = MaterialTheme.typography.titleLarge
            )
        }, navigationIcon = {
            IconButton(onClick = {

            }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        }, colors = TopAppBarDefaults.topAppBarColors(
            containerColor = textColor
        )
        )
    }, floatingActionButton = {
        if (plant != null) {
            FloatingActionButton(
                onClick = {
                    homeViewmodel.addPlant(
                        plantId = plantId.toString(),
                        commonName = plant.commonName,
                        familyName = plant.familyName,
                        scientificName = plant.scientificName,
                        species = plant.species,
                        imageUrl = plant.imageUrl,
                        family = plant.family,
                        genus = plant.genus,
                        bibliography = plant.bibliography,
                        observations = plant.observations,
                        vegetable = plant.vegetable,
                        onResult = { condition, message ->

                            if (condition) {
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                            }

                        },


                        )


                },
                containerColor = textColor,
                modifier = Modifier.padding(16.dp, vertical = 70.dp)
            ) {
                Icon(
                    imageVector = if (isLiked) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = "Like",
                    tint = Color.White
                )
            }
        }
    }, containerColor = bgColor
    ) { paddingValues ->

        when {
            plantDetailsUiStates.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator(color = textColor)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Loading plant details...",
                            color = textColor,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

            plantDetailsUiStates.error.isNotEmpty() -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = plantDetailsUiStates.error,
                        color = Color.Red,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            plant != null -> {

                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(paddingValues)
                        .padding(horizontal = 20.dp, vertical = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        model = plant.imageUrl,
                        contentDescription = plant.commonName,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(280.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color.White),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            PlantInfoItem("Scientific Name", plant.scientificName, textColor)
                            PlantInfoItem("Family", plant.family, textColor)
                            PlantInfoItem("Genus", plant.genus, textColor)
                            PlantInfoItem("Species", plant.species, textColor)
                            PlantInfoItem("Bibliography", plant.bibliography, textColor)
                            PlantInfoItem(
                                "Edibility",
                                if (plant.vegetable) "Edible (Vegetable)" else "Non-vegetable Plant",
                                textColor
                            )
                            PlantInfoItem("observations", plant.observations, textColor)
                        }
                    }
                }
            }

            else -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No plant data available.",
                        color = textColor,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

@Composable
private fun PlantInfoItem(label: String, value: String, color: Color) {
    Column(modifier = Modifier.padding(vertical = 6.dp)) {
        Text(
            text = label, style = MaterialTheme.typography.labelMedium.copy(
                color = color.copy(alpha = 0.8f), fontWeight = FontWeight.SemiBold
            )
        )
        Text(
            text = value, style = MaterialTheme.typography.bodyLarge.copy(
                color = color, fontStyle = FontStyle.Italic
            )
        )
    }
}
