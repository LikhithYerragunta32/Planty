package uk.ac.tees.mad.planty.presentation.Navigation

import AuthScreen


import SignUpScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.plantapp.ui.screens.PlantDetailScreen
import com.google.firebase.auth.FirebaseAuth
import okhttp3.Route

import uk.ac.tees.mad.planty.presentation.AuthScreens.HomeScreen
import uk.ac.tees.mad.planty.presentation.AuthScreens.LogInScreen

import uk.ac.tees.mad.planty.presentation.HIltViewmodels.AuthViewmodel
import uk.ac.tees.mad.planty.presentation.HIltViewmodels.HomeViewmodel
import uk.ac.tees.mad.planty.presentation.Screens.MyPlantScreen
import uk.ac.tees.mad.planty.presentation.Screens.ProfileScreen

@Composable
fun NavigationCompose(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    authViewModel: AuthViewmodel,
    homeViewModel: HomeViewmodel,
) {

    val auth = FirebaseAuth.getInstance()

//    AdgamaUser123@
//    Adg@2025Safe
//    https://plant.id/api/v3
//    ENZF56UDT2C8iUX2ueep5OoNTDILmnsGLZRbOWtlYPo2K1hRLJ

//    Trefle.io Api Key
//    usr-wxVNIpI_JSulNq0RAdDxUavms5b4hBIH9tv7GUvSQPc

    var currentUser by remember { mutableStateOf(auth.currentUser) }

    DisposableEffect(Unit) {

        val listener = FirebaseAuth.AuthStateListener {
            currentUser = it.currentUser
        }
        auth.addAuthStateListener(listener)
        onDispose { auth.removeAuthStateListener(listener) }


    }

    val startDestination = if (currentUser == null) {
        Routes.AuthScreen
    } else {
        Routes.HomeScreen
    }


    NavHost(navController = navController, startDestination = startDestination) {


        composable<Routes.AuthScreen> {


            AuthScreen(
                navController = navController,
            )

        }

        composable<Routes.SingInScreen> {


            SignUpScreen(
                authViewModel = authViewModel, navController = navController
            )

        }

        composable<Routes.HomeScreen> {


            HomeScreen(
                homeViewModel = homeViewModel,
                authViewModel = authViewModel,
                navController = navController
            )

        }

        composable<Routes.LogInScreen> {


            LogInScreen(
                authViewModel = authViewModel,

                navController = navController,
            )

        }
        composable<Routes.MyPlantScreen> {

            MyPlantScreen()
        }
        composable<Routes.ProfileScreen> {

            ProfileScreen()
        }
        composable<Routes.PlantDetailScreen> {
            val toRoute = it.toRoute<Routes.PlantDetailScreen>()
            PlantDetailScreen(
                homeViewmodel = homeViewModel,
                plantId = toRoute.plantId
            )

        }

    }

}