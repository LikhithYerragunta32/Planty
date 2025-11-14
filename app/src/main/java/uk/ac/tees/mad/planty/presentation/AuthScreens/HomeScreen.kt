package uk.ac.tees.mad.planty.presentation.AuthScreens


import android.Manifest
import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Base64
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController

import coil3.compose.AsyncImage
import uk.ac.tees.mad.planty.R
import uk.ac.tees.mad.planty.domain.model.DomainPlantData
import uk.ac.tees.mad.planty.presentation.HIltViewmodels.AuthViewmodel
import uk.ac.tees.mad.planty.presentation.HIltViewmodels.HomeViewmodel
import uk.ac.tees.mad.planty.presentation.UtilScreens.PlantCard
import java.io.File

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewmodel,
    authViewModel: AuthViewmodel,
    navController: NavHostController,
) {
    val context = LocalContext.current

    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()

// URI
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val defaultUri = Uri.parse(
        "${ContentResolver.SCHEME_ANDROID_RESOURCE}://${context.packageName}/${R.drawable.default_profile}"
    )

    val uri: Uri = (if (selectedImageUri == null) {
        defaultUri
    } else {
        selectedImageUri!!
    }) as Uri

    //isPermission
    var isCameraGranted by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context, Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    //Permission
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        isCameraGranted = granted

    }

    //    File Initializer
    val imageUri = rememberSaveable {

        val imageFile = File.createTempFile(
            "photo_", ".jpg", context.cacheDir
        )

        FileProvider.getUriForFile(
            context, "${context.packageName}.provider", imageFile

        )
    }


// CAMERA
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            selectedImageUri = imageUri

        }
    }


    //android 13
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(), onResult = { uri ->
            if (uri != null) {
                selectedImageUri = uri
            } else {
                Toast.makeText(context, "No image selected", Toast.LENGTH_SHORT).show()
            }
        })

//android 12
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(), onResult = { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val uri = result.data?.data
                if (uri != null) {
                    selectedImageUri = uri
                }
            } else {
                Toast.makeText(context, "No image selected", Toast.LENGTH_SHORT).show()
            }
        })


    var showDialog by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFF0F4E8),
                        Color(0xFFE8F5E9)
                    )
                )
            )
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.Top)
    ) {
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = {
                    Text(
                        "Select plant image", color = Color(0xFF1B5E20)
                    )
                },
                text = {
                    Text(
                        "Choose an option to select image",
                        color = Color(0xFF1B5E20)
                    )
                },
                confirmButton = {
                    TextButton(onClick = {

                        if (isCameraGranted) {

                            cameraLauncher.launch(imageUri)

                        } else {

                            permissionLauncher.launch(Manifest.permission.CAMERA)

                        }

                        showDialog = false


                    }) {
                        Text(
                            "Camera", color = Color(0xFF1B5E20)
                        )
                    }


                },
                dismissButton = {
                    TextButton(onClick = {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {


                            photoPickerLauncher.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )


                        } else {

                            val intent = Intent(
                                Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                            )
                            galleryLauncher.launch(intent)
                        }
                        showDialog = false
                    }) {
                        Text(
                            "Storage", color = Color(0xFF1B5E20)
                        )
                    }
                },
                shape = RoundedCornerShape(16.dp),
                containerColor = Color(0xFFE8FDEA)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))

//        Text(
//            text = "Plant Identifier",
//            fontSize = 32.sp,
//            fontWeight = FontWeight.Bold,
//            color = Color(0xFF1B5E20)
//        )


        if (selectedImageUri != null) {

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(320.dp),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                AsyncImage(
                    model = selectedImageUri,
                    contentDescription = "Selected plant image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        } else {

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(320.dp)
                    .clickable {
                        showDialog = true
                    },
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                border = BorderStroke(
                    width = 2.dp,
                    color = Color(0xFF81C784)
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.PhotoLibrary,
                        contentDescription = "Upload image",
                        modifier = Modifier.size(64.dp),
                        tint = Color(0xFF2E7D32)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Tap to select image",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF2E7D32)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Choose a plant photo from your gallery",
                        fontSize = 12.sp,
                        color = Color(0xFF558B2F)
                    )
                }
            }
        }


        val samplePlants = listOf(
            DomainPlantData(
                plantName = "Solanum lycopersicum",
                probability = 0.92,
                commonNames = listOf("Tomato", "Garden tomato"),
                infoUrl = "https://en.wikipedia.org/wiki/Tomato"
            ),
            DomainPlantData(
                plantName = "Lactuca sativa",
                probability = 0.65,
                commonNames = listOf("Lettuce", "Garden lettuce"),
                infoUrl = "https://en.wikipedia.org/wiki/Lettuce"
            ),
            DomainPlantData(
                plantName = "Capsicum annuum",
                probability = 0.38,
                commonNames = listOf("Bell pepper", "Sweet pepper"),
                infoUrl = null
            ),
            DomainPlantData(
                plantName = "Rosa canina",
                probability = 0.25,
                commonNames = listOf("Dog rose", "Wild rose"),
                infoUrl = null
            ),
            DomainPlantData(
                plantName = "Fragaria × ananassa",
                probability = 0.88,
                commonNames = listOf("Strawberry"),
                infoUrl = "https://en.wikipedia.org/wiki/Strawberry"
            )
        )


            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(samplePlants) { plant ->
                    PlantCard(plantData = plant)
                }
            }











        Spacer(modifier = Modifier.weight(1f))

        // Buttons Row
        if (selectedImageUri != null) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                Button(
                    onClick = {

                        val base64Image = uriToBase64(context, uri)

                        homeViewModel.fetchPlantData(
                            image = base64Image
                        )

                    },
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF2E7D32)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {

                    if (uiState.isLoading) {
                        CircularProgressIndicator(
                            color = Color.White,
                            strokeWidth = 3.dp,
                            modifier = Modifier.size(24.dp)
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            modifier = Modifier.size(20.dp),
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Search",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                    }

                }


                OutlinedButton(
                    onClick = {

                        showDialog = true

                    },
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    border = BorderStroke(2.dp, Color(0xFF2E7D32)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Select again",
                        modifier = Modifier.size(20.dp),
                        tint = Color(0xFF2E7D32)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Change",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF2E7D32)
                    )
                }
            }
        } else {
            Button(
                onClick = {
                    showDialog = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2E7D32)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.PhotoLibrary,
                    contentDescription = "Select image",
                    modifier = Modifier.size(20.dp),
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Select Image",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }
        }

        Text(
            text = "Tips: Take clear photos in good lighting",
            fontSize = 12.sp,
            color = Color(0xFF558B2F),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}


fun uriToBase64(context: Context, imageUri: Uri): String {
    val inputStream = context.contentResolver.openInputStream(imageUri)
    val bytes = inputStream?.readBytes()
    inputStream?.close()
    return Base64.encodeToString(bytes, Base64.DEFAULT)
}