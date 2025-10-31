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
import com.google.firebase.auth.FirebaseAuth

import uk.ac.tees.mad.planty.presentation.AuthScreens.HomeScreen
import uk.ac.tees.mad.planty.presentation.AuthScreens.LogInScreen

import uk.ac.tees.mad.planty.presentation.HIltViewmodels.AuthViewmodel
import uk.ac.tees.mad.planty.presentation.HIltViewmodels.HomeViewmodel

@Composable
fun NavigationCompose(modifier: Modifier = Modifier, navController: NavHostController, authViewModel: AuthViewmodel, homeViewModel: HomeViewmodel) {

    val auth = FirebaseAuth.getInstance()


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
                authViewModel = authViewModel,
                navController = navController
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


    }

}