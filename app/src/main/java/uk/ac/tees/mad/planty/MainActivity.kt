package uk.ac.tees.mad.planty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import uk.ac.tees.mad.planty.domain.reposiotry.NetworkConnectivityObserver
import uk.ac.tees.mad.planty.domain.reposiotry.NetworkStatus

import uk.ac.tees.mad.planty.presentation.HIltViewmodels.AuthViewmodel
import uk.ac.tees.mad.planty.presentation.HIltViewmodels.HomeViewmodel
import uk.ac.tees.mad.planty.presentation.Navigation.NavigationCompose
import uk.ac.tees.mad.planty.presentation.UtilScreens.NetworkStatusBar
import uk.ac.tees.mad.planty.ui.theme.PlantyTheme
import javax.inject.Inject


@AndroidEntryPoint

class MainActivity : ComponentActivity() {


    @Inject
    lateinit var connectivityObserver: NetworkConnectivityObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()
        enableEdgeToEdge()
        setContent {

            val authViewModel: AuthViewmodel = hiltViewModel()
            val homeViewmodel: HomeViewmodel = hiltViewModel()
            val navController: NavHostController = rememberNavController()
            var message by rememberSaveable { mutableStateOf("") }
            var bgColors by remember { mutableStateOf(Color.Red) }
            var showStatusBar by remember { mutableStateOf(false) }
            val status by connectivityObserver.networkStatus.collectAsStateWithLifecycle()


            LaunchedEffect(key1 = status) {


                when (status) {


                    NetworkStatus.Connected -> {
                        message = "Connected To Internet"
                        bgColors = Color.Green
                        delay(2000)
                        showStatusBar = false
                    }


                    NetworkStatus.Disconnected -> {

                        showStatusBar = true
                        message = "No Internet Connected !!"
                        bgColors = Color.Red

                    }
                }
            }


            PlantyTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        NetworkStatusBar(
                            showMessageBar = showStatusBar,
                            message = message,
                            backgroundColor = bgColors
                        )
                    }) { innerPadding ->


                    NavigationCompose(
                        modifier = Modifier.padding(innerPadding),
                        authViewModel = authViewModel,
                        homeViewModel = homeViewmodel,
                        navController = navController
                    )


                }
            }
        }
    }
}

