import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.sin
import kotlin.math.cos
import androidx.navigation.NavController
import uk.ac.tees.mad.planty.presentation.Navigation.Routes

import uk.ac.tees.mad.planty.R

@Composable
fun AuthScreen(modifier: Modifier = Modifier, navController: NavController) {
    val cornerShape = RoundedCornerShape(14.dp)
    val bgColo = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF4CAF50),
            Color(0xFF81C784)  ,
            Color(0xFF4CAF50)
        )
    )
    Box(
        modifier = modifier
            .fillMaxSize().
                padding()
            .background(
                brush = bgColo
            )
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth().padding(10.dp)
                .align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(60.dp))

            

            Spacer(modifier = Modifier.height(16.dp))


            Image(
                painter = painterResource(id = R.drawable.authpic),
                contentDescription = "Weather illustration",
                modifier = Modifier
                    .fillMaxWidth(),
                alignment = Alignment.Center
            )




            Spacer(modifier = Modifier.height(20.dp))


            Text(
                text = "Nurture your plants with AI — get smart insights, weather updates, and care guidance all in one place",
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = Color(0xFF000000),
                    lineHeight = 22.sp,
                    fontWeight = FontWeight.Light
                ),
                modifier = Modifier.padding(horizontal = 20.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth().padding(16.dp)
                .align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedButton(
                onClick = {
                   navController.navigate(Routes.SingInScreen)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                    .height(52.dp),
                shape = cornerShape,
                border = BorderStroke(2.dp, Color(0xFF009A06)),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.Green
                )
            ) {
                if (false) {
                    CircularProgressIndicator(
                        color = Color.Black,
                        strokeWidth = 2.dp,
                        modifier = Modifier.size(30.dp)
                    )
                } else {

                    Text(
                        "Let's get started",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                }
            }


            TextButton(onClick = { navController.navigate(Routes.LogInScreen) }) {
                Text(
                    buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        ) { append("Already have an account? ") }

                        withStyle(
                            style = SpanStyle(
                                color =  Color(0xFF00FF1E),
                                textDecoration = TextDecoration.Underline,
                                fontWeight = FontWeight.Medium
                            )
                        ) { append("Log in") }
                    },
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}


@Preview(showBackground = true, name = "Auth Screen - Planty")
@Composable
fun AuthScreenPreview() {
    val cornerShape = RoundedCornerShape(14.dp)
    val bgColor = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF4CAF50),
            Color(0xFF81C784),
            Color(0xFF4CAF50)
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = bgColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(60.dp))

            Image(
                painter = painterResource(id = R.drawable.authpic),
                contentDescription = "Planty illustration",
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Nurture your plants with AI — get smart insights, weather updates, and care guidance all in one place",
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = Color.Black,
                    lineHeight = 22.sp,
                    fontWeight = FontWeight.Light
                ),
                modifier = Modifier.padding(horizontal = 20.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedButton(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                    .height(52.dp),
                shape = cornerShape,
                border = BorderStroke(2.dp, Color(0xFF009A06)),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.Green
                )
            ) {
                Text(
                    "Let's get started",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            TextButton(onClick = { }) {
                Text(
                    buildAnnotatedString {
                        withStyle(SpanStyle(color = MaterialTheme.colorScheme.onBackground)) {
                            append("Already have an account? ")
                        }
                        withStyle(
                            SpanStyle(
                                color = Color(0xFF00FF1E),
                                textDecoration = TextDecoration.Underline,
                                fontWeight = FontWeight.Medium
                            )
                        ) { append("Log in") }
                    },
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}