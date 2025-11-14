package uk.ac.tees.mad.planty.presentation.UtilScreens

import android.R.attr.fontWeight
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uk.ac.tees.mad.planty.domain.model.DomainPlantData
import java.time.format.TextStyle


// Function to determine color based on probability
fun getProbabilityColor(probability: Double): Color {
    return when {
        probability > 0.7 -> Color(0xFF2E7D32)
        probability > 0.4 -> Color(0xFF839F00)
        else -> Color(0xFFB40000)
    }
}

@Composable
fun PlantCard(plantData: DomainPlantData) {
    val probColor = getProbabilityColor(plantData.probability)
    val percentage = String.format("%.1f%%", plantData.probability * 100)
    val textColor = Color(0xFF2E7D32)
    val backgroundColor = Color(0xFFE8F5E9)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(2.dp, textColor),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .width(200.dp)
                .height(100.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            // Left side: Plant name and common names
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = plantData.plantName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Text(
                    text = "Common names: ${plantData.commonNames}",
                    fontSize = 14.sp,
                    fontStyle = FontStyle.Italic,
                    color = Color.DarkGray
                )
            }

            // Right side: Probability at top
            Box(
                modifier = Modifier
                    .background(Color(0xFFE8F5E9), shape = RoundedCornerShape(8.dp))
                    .padding(top = 5.dp, end = 5),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = percentage,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = getProbabilityColor(plantData.probability)
                )
            }
        }

    }
}
