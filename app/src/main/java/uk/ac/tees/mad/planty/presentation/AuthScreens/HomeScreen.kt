package uk.ac.tees.mad.planty.presentation.AuthScreens


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement

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

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon


import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.navigation.NavHostController
import coil3.Uri
import coil3.compose.AsyncImage

import uk.ac.tees.mad.planty.presentation.HIltViewmodels.AuthViewmodel
import uk.ac.tees.mad.planty.presentation.HIltViewmodels.HomeViewmodel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewmodel,
    authViewModel: AuthViewmodel,
    navController: NavHostController
) {
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }



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
        // Header
        Text(
            text = "Plant Identifier",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1B5E20)
        )
        Text(
            text = "Upload a plant image to discover its species",
            fontSize = 14.sp,
            color = Color(0xFF558B2F),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))


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
                    .clickable {  },
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

        Spacer(modifier = Modifier.height(16.dp))

        // Buttons Row
        if (selectedImageUri != null) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Search Button
                Button(
                    onClick = { /* Handle search */ },
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF2E7D32)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
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


                OutlinedButton(
                    onClick = {  },
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
                onClick = {  },
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

        Spacer(modifier = Modifier.weight(1f))


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


