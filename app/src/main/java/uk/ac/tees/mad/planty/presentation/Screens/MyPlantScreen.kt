package uk.ac.tees.mad.planty.presentation.Screens

import android.R.attr.strokeWidth
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import coil3.compose.AsyncImage
import uk.ac.tees.mad.planty.data.local.PlantEntity
import uk.ac.tees.mad.planty.presentation.HIltViewmodels.HomeViewmodel
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment

import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import okhttp3.Route
import uk.ac.tees.mad.planty.presentation.Navigation.BottomNavigation
import uk.ac.tees.mad.planty.presentation.Navigation.Routes


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyPlantScreen(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewmodel,
    navController: NavHostController,
    onBackClick: () -> Unit = {}
) {
    val context = LocalContext.current
    val bgColor = Color(0xFFE8F5E9)
    val textColor = Color(0xFF2E7D32)

    // Fetch saved plants
    LaunchedEffect(Unit) {
        homeViewModel.getSavedPlant()
    }

    val plantList by homeViewModel.savedPlant.collectAsState()
    var isLoading = homeViewModel.isLoading.collectAsState().value
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "My Plants",
                        color = Color.White,
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = textColor
                )
            )
        },
        bottomBar = {
            BottomNavigation(navController)
        },
        containerColor = bgColor
    ) { paddingValues ->

        if (plantList.isEmpty()) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                if (isLoading) {
                    CircularProgressIndicator( modifier = Modifier.size(32.dp),
                        color = Color(0xFF2E7D32),
                        strokeWidth = 2.dp)
                } else {
                    Text(
                        text = "No plants saved yet.",
                        color = textColor,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

            }
        } else {

            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(plantList) { plant ->
                    PlantCard(
                        plant = plant,
                        textColor = textColor,
                        onCardClick={
                            navController.navigate(Routes.PlantDetailScreen(it.toInt()))
                        },
                        onDeleteClick = {
                            homeViewModel.removeCity(
                                plant = it,
                                onResult = { condition, message ->
                                    if (condition) {
                                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                                        homeViewModel.getSavedPlant()
                                    } else {
                                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                                    }
                                }
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun PlantCard(
    plant: PlantEntity,
    textColor: Color,
    onDeleteClick: (PlantId: String) -> Unit,
    onCardClick: (PlantId: String) -> Unit,
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onCardClick(plant.plantId)
            }
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = plant.imageUrl,
                contentDescription = plant.commonName,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White),
                contentScale = ContentScale.Crop
            )


            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = plant.commonName,
                    color = textColor,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = plant.scientificName,
                    color = textColor.copy(alpha = 0.8f),
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Family: ${plant.familyName}",
                    color = textColor.copy(alpha = 0.7f),
                    style = MaterialTheme.typography.bodySmall
                )
            }

            IconButton(
                onClick = {
                    onDeleteClick(plant.plantId)
                },
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE8F5E9))
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color(0xFF2E7D32)
                )
            }
        }
    }
}
