package uk.ac.tees.mad.planty.di


import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext

import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import uk.ac.tees.mad.planty.data.repoImpl.NetworkConnectivityObserverImpl
import uk.ac.tees.mad.planty.domain.reposiotry.NetworkConnectivityObserver

import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object HiltModule {

    @Provides
    @Singleton
    fun provideCoroutineScope(): CoroutineScope = CoroutineScope(kotlinx.coroutines.Dispatchers.IO)

    @Provides
    fun provideNetworkConnectivityObserver(
        @ApplicationContext context: Context,
        scope: CoroutineScope,
    ): NetworkConnectivityObserver {
        return NetworkConnectivityObserverImpl(context, scope)
    }


}