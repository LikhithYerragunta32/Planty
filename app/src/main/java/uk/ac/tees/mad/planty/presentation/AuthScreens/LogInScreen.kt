package uk.ac.tees.mad.planty.presentation.AuthScreens

import android.R.attr.onClick
import android.R.attr.textColor
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.atomicfu.TraceBase.None.append
import kotlinx.coroutines.delay
import uk.ac.tees.mad.planty.R
import uk.ac.tees.mad.planty.presentation.HIltViewmodels.AuthViewmodel
import uk.ac.tees.mad.planty.presentation.Navigation.Routes

@Composable
fun LogInScreen(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewmodel,
    navController: NavHostController,
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var Triggeer by rememberSaveable { mutableStateOf(false) }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var isLoading by rememberSaveable { mutableStateOf(false) }

    val bgColor1 = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF4CAF50),
            Color(0xFF81C784),


            Color(0xFF4CAF50)
        )
    )
    val gradientBrush = Brush.horizontalGradient(
        colors = listOf(
            Color(0xFF4CAF50),
            Color(0xFF81C784),
            Color(0xFF4CAF50)
        )
    )

    val textColor = Color(0xFF2E7D32)
    val bgColor = Color(0xFFE8F5E9)

    LaunchedEffect(Triggeer) {
        delay(3000)
        passwordVisible = !passwordVisible
    }

    val context = LocalContext.current
    val cornerShape = RoundedCornerShape(14.dp)

    // Email validation
    val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    // Password validation
    val passwordRegex = Regex("^(?=.*[!@#\$%^&*(),.?\":{}|<>]).{6,10}\$")
    val isPasswordValid = passwordRegex.matches(password)

    // Enable button only if fields valid
    val isFormValid = isEmailValid && isPasswordValid

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding()
            .background(
                brush = bgColor1
            )
    ) {


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(100.dp))

            // Email
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text("Email", color = textColor.copy(alpha = 0.6f)) },
                textStyle = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp),
                modifier = Modifier.fillMaxWidth(),
                shape = cornerShape,
//                isError = email.isNotEmpty() && !isEmailValid,
                colors = TextFieldDefaults.colors(
                    focusedTextColor = textColor,
                    unfocusedTextColor = textColor,
                    cursorColor = textColor,
                    disabledTextColor = textColor,
                    focusedContainerColor = bgColor,
                    unfocusedContainerColor = bgColor,
                    disabledContainerColor = bgColor,
                    focusedIndicatorColor = textColor,
                    unfocusedIndicatorColor = textColor.copy(alpha = 0.5f),
                    disabledIndicatorColor = textColor.copy(alpha = 0.3f),
                    focusedLabelColor = textColor,
                    unfocusedLabelColor = textColor.copy(alpha = 0.8f),
                    disabledLabelColor = textColor.copy(alpha = 0.5f)
                )
            )

            Spacer(modifier = Modifier.height(18.dp))

            // Password
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text("Password", color = textColor.copy(alpha = 0.6f)) },
                textStyle = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp),
                modifier = Modifier.fillMaxWidth(),
                shape = cornerShape,
                visualTransformation = if (passwordVisible) VisualTransformation.None
                else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = {
                        passwordVisible = !passwordVisible
                        Triggeer = !Triggeer
                    }) {
                        Icon(
                            painter = painterResource(
                                if (passwordVisible) R.drawable.baseline_visibility_24
                                else R.drawable.outline_visibility_off_24
                            ),
                            contentDescription = null, tint = Color.Black
                        )
                    }
                },
//                isError = password.isNotEmpty() && !isPasswordValid,
                colors = TextFieldDefaults.colors(
                    focusedTextColor = textColor,
                    unfocusedTextColor = textColor,
                    cursorColor = textColor,
                    disabledTextColor = textColor,
                    focusedContainerColor = bgColor,
                    unfocusedContainerColor = bgColor,
                    disabledContainerColor = bgColor,
                    focusedIndicatorColor = textColor,
                    unfocusedIndicatorColor = textColor.copy(alpha = 0.5f),
                    disabledIndicatorColor = textColor.copy(alpha = 0.3f),
                    focusedLabelColor = textColor,
                    unfocusedLabelColor = textColor.copy(alpha = 0.8f),
                    disabledLabelColor = textColor.copy(alpha = 0.5f)
                )
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            OutlinedButton(
                onClick = {
                    if (isFormValid) {
                        authViewModel.logIn(
                            email = email,
                            passkey = password,
                            onResult = { message, success ->
                                if (success) {
                                    isLoading = true
                                    navController.navigate(Routes.HomeScreen)
                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                                    isLoading = true
                                } else {
                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                                }
                            }

                            ,

                            )
                    } else {
                        Toast.makeText(
                            context,
                            "Please enter valid email and password",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

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
                if (isLoading) {
                    CircularProgressIndicator(
                        color = Color.Black,
                        strokeWidth = 2.dp,
                        modifier = Modifier.size(30.dp)
                    )
                } else {
                    Text(
                        "Log In",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
            }


            Spacer(modifier = Modifier.height(16.dp))


            TextButton(onClick = { navController.navigate(Routes.SingInScreen) }) {
                Text(
                    buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        ) { append("Don’t have an account? ") }

                        withStyle(
                            style = SpanStyle(
                                color = Color(0xFF00FF1E),
                                textDecoration = TextDecoration.Underline,
                                fontWeight = FontWeight.Medium
                            )
                        ) { append("Sign Up") }
                    },
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}