package uk.ac.tees.mad.planty.presentation.UtilScreens

import android.R.attr.fontWeight
import android.R.attr.onClick
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uk.ac.tees.mad.planty.domain.model.DomainPlantData


fun getProbabilityColor(probability: Double): Color {
    return when {
        probability > 0.7 -> Color(0xFF2E7D32)
        probability > 0.4 -> Color(0xFF839F00)
        else -> Color(0xFFB40000)
    }
}

@Composable
fun PlantCard(plantData: DomainPlantData, onCardClick: (plantName: String) -> Unit) {

    val probColor = getProbabilityColor(plantData.probability)
    val percentage = String.format("%.1f%%", plantData.probability * 100)
    val textColor = Color(0xFF2E7D32)


    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()


    val backgroundColor by animateColorAsState(
        if (isPressed) Color(0xFFF1FDBC) else Color(0xFFE8F5E9),
        label = "ColorChange"
    )




    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
            .width(200.dp)
            .height(130.dp)
            .clickable {

            },
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(2.dp, textColor),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    onCardClick(plantData.plantName ?: "")
                }) {
            Row(
                modifier = Modifier
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {

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
                Box(
                    modifier = Modifier
                        .background(Color(0xFFE8F5E9), shape = RoundedCornerShape(8.dp))
                        .padding(top = 5.dp, end = 5.dp),
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
}

