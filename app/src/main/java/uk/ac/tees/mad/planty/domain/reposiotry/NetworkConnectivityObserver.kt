package uk.ac.tees.mad.planty.domain.reposiotry


import kotlinx.coroutines.flow.StateFlow


interface NetworkConnectivityObserver {


    val networkStatus: StateFlow<NetworkStatus>


}

sealed class NetworkStatus {
    data object Connected: NetworkStatus()
    data object Disconnected: NetworkStatus()
}