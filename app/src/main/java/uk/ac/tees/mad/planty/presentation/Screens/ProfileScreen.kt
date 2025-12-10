package uk.ac.tees.mad.planty.presentation.Screens

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.google.android.play.integrity.internal.n
import uk.ac.tees.mad.careerconnect.data.remote.uriToByteArray
import uk.ac.tees.mad.planty.R
import uk.ac.tees.mad.planty.presentation.HIltViewmodels.HomeViewmodel
import uk.ac.tees.mad.planty.presentation.Navigation.BottomNavigation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavHostController,
    homeViewmodel: HomeViewmodel,
    modifier: Modifier = Modifier,
) {
    LaunchedEffect(Unit) {

        homeViewmodel.fetchCurrentDonerData()


    }

    val context = LocalContext.current
    var update by remember { mutableStateOf(false) }


    val currentUser = homeViewmodel.currentUserData.collectAsState().value
    var newName by rememberSaveable { mutableStateOf("") }
    var isLoading by rememberSaveable { mutableStateOf(false) }
    val cornerShape = RoundedCornerShape(14.dp)
    var isEditing by rememberSaveable { mutableStateOf(false) }
    var showError by rememberSaveable { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val freshUrl = "${currentUser.profileImageUrl}?t=${System.currentTimeMillis()}"
    val imageRequest = ImageRequest.Builder(context).data(freshUrl).crossfade(true)
        .diskCachePolicy(CachePolicy.ENABLED).memoryCachePolicy(CachePolicy.ENABLED).build()
    val painter = rememberAsyncImagePainter(model = imageRequest)
    // Access state directly (no collectAsState needed)
    val state by painter.state.collectAsState()
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val bgColor = Color(0xFFE8F5E9)
    val textColor = Color(0xFF2E7D32)


    val defaulImagetUri = Uri.parse(
        "${ContentResolver.SCHEME_ANDROID_RESOURCE}://${context.packageName}/${R.drawable.default_profile}"

    )

    val imageUri: Uri = if (selectedImageUri == null) {
        defaulImagetUri
    } else {
        selectedImageUri!!
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
                selectedImageUri = uri
            } else {
                Toast.makeText(context, "No image selected", Toast.LENGTH_SHORT).show()
            }
        })
    var expanded by rememberSaveable { mutableStateOf(false) }
    var showDialogLogout by rememberSaveable { mutableStateOf(false) }
    var showDialogNotification by rememberSaveable { mutableStateOf(false) }
    LaunchedEffect(update) {

        homeViewmodel.fetchCurrentDonerData()


    }


    Scaffold(
        modifier.fillMaxSize(), bottomBar = {


            BottomNavigation(navController)
        }, topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Profile", color = Color.White, fontSize = 22.sp
                    )
                }, actions = {

                    Box {
                        IconButton(onClick = { expanded = !expanded }) {
                            Icon(
                                imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.Settings,
                                contentDescription = "Settings",
                                tint = Color.Black
                            )
                        }

                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            containerColor = bgColor,
                            tonalElevation = 4.dp,
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            DropdownMenuItem(
                                text = { Text("App Notifications", color = textColor) },
                                onClick = {
                                    expanded = false
                                    showDialogNotification = true
                                },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Notifications,
                                        contentDescription = "Notifications",
                                        tint = textColor
                                    )
                                }
                            )

                            DropdownMenuItem(
                                text = { Text("Log Out", color = textColor) },
                                onClick = {
                                    expanded = false
                                    showDialogLogout = true
                                },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.ExitToApp,
                                        contentDescription = "Log out",
                                        tint = textColor
                                    )
                                }
                            )
                        }
                    }

                }, colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF2E7D32))
            )
        }, containerColor = Color(0xFFE8F5E9)

    ) {

            paddingValues ->


        if (showDialogLogout) {
            AlertDialog(
                onDismissRequest = {
                    expanded = false
                    showDialogLogout = false
                },
                title = { Text("Logout", color = textColor) },
                text = { Text("Are you sure you want to log out?", color = textColor) },
                confirmButton = {
                    TextButton(onClick = {
                        homeViewmodel.logoutUser()
                        expanded = false
                    }) {
                        Text("Yes", color = textColor)
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        expanded = false
                        showDialogLogout = false
                    }) {
                        Text("No", color = textColor)
                    }
                },
                containerColor = bgColor,
                tonalElevation = 4.dp,
                shape = RoundedCornerShape(16.dp)
            )
        }

        if (showDialogNotification) {
            var isNotificationEnabled = isAppNotificationEnabled(context)

            AlertDialog(
                onDismissRequest = {
                    expanded = false
                    showDialogNotification = false
                },
                title = { Text("Notifications", color = textColor) },
                text = {
                    Text(
                        if (isNotificationEnabled)
                            "Notifications are currently ON. Do you want to turn them OFF?"
                        else
                            "Notifications are currently OFF. Do you want to turn them ON?",
                        color = textColor
                    )
                },
                confirmButton = {
                    TextButton(onClick = {
                        val intent = Intent().apply {
                            action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
                            putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
                        }
                        context.startActivity(intent)
                        showDialogNotification = false
                        expanded = false
                        isNotificationEnabled = isAppNotificationEnabled(context)
                    }) {
                        Text(
                            if (isNotificationEnabled) "Turn Off" else "Turn On",
                            color = textColor
                        )
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        expanded = false
                        showDialogNotification = false
                    }) {
                        Text("No", color = textColor)
                    }
                },
                containerColor = bgColor,
                tonalElevation = 4.dp,
                shape = RoundedCornerShape(16.dp)
            )
        }





        Box(
            modifier.padding(paddingValues)
        ) {
            Spacer(modifier = Modifier.height(25.dp))
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(contentAlignment = Alignment.BottomEnd) {

                    if (selectedImageUri != null) {

                        AsyncImage(
                            model = selectedImageUri,
                            contentDescription = "Profile Image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(100.dp)
                                .clip(CircleShape)
                        )
                    } else if (state is AsyncImagePainter.State.Loading) {
                        Box(
                            contentAlignment = Alignment.Center, modifier = Modifier.size(100.dp)
                        ) {
                            AsyncImage(
                                model = R.drawable.pf,
                                contentDescription = "Profile Image",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(100.dp)
                                    .clip(CircleShape)
                            )


                            CircularProgressIndicator(
                                color = Color.Black,

                                strokeWidth = 2.dp, modifier = Modifier.size(30.dp)

                            )
                        }


                    } else if (state is AsyncImagePainter.State.Success) {

                        AsyncImage(
                            model = imageRequest,
                            contentDescription = "Profile Image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(100.dp)
                                .clip(CircleShape)
                        )
                    } else {
                        AsyncImage(
                            model = R.drawable.pf,
                            contentDescription = "Profile Image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(100.dp)
                                .clip(CircleShape)
                        )
                    }
                    if (isEditing) {
                        IconButton(
                            onClick = {

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
                            }, modifier = Modifier
                                .size(35.dp)
                                .background(
                                    color = Color(0xFF4B914F), CircleShape
                                )
                                .size(30.dp)

                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Edit Image",
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))


                }

                Spacer(modifier = Modifier.height(8.dp))
                if (isEditing == false) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp, horizontal = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Edit your profile",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.Medium
                            ),
                            color = textColor
                        )

                        IconButton(onClick = {
                            isEditing = true
                        }) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Edit Profile",
                                tint = textColor
                            )
                        }
                    }


                }
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = if (isEditing) newName else currentUser.name,
                    enabled = isEditing,
                    onValueChange = { input ->
                        newName = input.split(" ").joinToString(" ") { word ->
                            if (word.isNotEmpty()) word.replaceFirstChar { it.uppercase() }
                            else word
                        }
                    },
                    label = { Text("Name", color = textColor) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = cornerShape,
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

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = if (isEditing) currentUser.email else currentUser.email,
                    enabled = false,
                    onValueChange = {

                    },
                    label = {
                        Text(
                            " Email ", color = textColor
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = cornerShape,
                    isError = showError,
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
                Spacer(modifier = Modifier.height(32.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OutlinedButton(
                        onClick = { isEditing = false },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, textColor)
                    ) {
                        Text("Cancel", color = textColor)
                    }
                    Spacer(modifier = Modifier.weight(0.1f))
                    Button(


                        onClick = {


                            isLoading = true
                            val profielImageByteArray = imageUri.uriToByteArray(context)

                            profielImageByteArray?.let() {

                                homeViewmodel.updateProfile(
                                    ProfielImageByteArray = profielImageByteArray,
                                    name = if (newName.isNotBlank()) newName else currentUser.name,
                                    onResult = { message, boolean ->
                                        if (boolean) {
                                            Toast.makeText(
                                                context, message, Toast.LENGTH_SHORT
                                            ).show()

                                            isEditing = false
                                            isLoading = false

                                        } else {
                                            isLoading = false

                                            Toast.makeText(
                                                context, message, Toast.LENGTH_SHORT
                                            ).show()
                                        }

                                    },

                                    )

                            }

                            update = !update


                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF2E7D32)
                        )
                    ) {


                        if (isLoading) {
                            CircularProgressIndicator(
                                color = MaterialTheme.colorScheme.onBackground,
                                strokeWidth = 2.dp,
                                modifier = Modifier.size(20.dp)
                            )
                        } else {
                            Text(
                                "Update",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground,
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(48.dp))


            }


        }
    }

}

fun isAppNotificationEnabled(context: Context): Boolean {
    return NotificationManagerCompat.from(context).areNotificationsEnabled()
}