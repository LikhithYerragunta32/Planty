package uk.ac.tees.mad.planty.presentation.Navigation

import kotlinx.serialization.Serializable

sealed class NavRoutes {


    @Serializable
    data object AuthScreen

    @Serializable
    data object SingInScreen

    @Serializable
    data object LogInScreen

    @Serializable
    data object HomeScreen

}