package uk.ac.tees.mad.planty.presentation.Navigation

import kotlinx.serialization.Serializable


sealed class Routes {


    @kotlinx.serialization.Serializable
    data object AuthScreen

    @kotlinx.serialization.Serializable
    data object SingInScreen

    @kotlinx.serialization.Serializable
    data object LogInScreen

    @kotlinx.serialization.Serializable
    data object HomeScreen

    @Serializable
    data class MyPlantScreen(val test: String)

    @Serializable
    data object ProfileScreen


}