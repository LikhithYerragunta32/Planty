package uk.ac.tees.mad.planty.di


import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext

import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uk.ac.tees.mad.planty.data.remote.api.PlantApi
import uk.ac.tees.mad.planty.data.repoImpl.NetworkConnectivityObserverImpl
import uk.ac.tees.mad.planty.data.repoImpl.PlantRepositoryImpl
import uk.ac.tees.mad.planty.domain.reposiotry.NetworkConnectivityObserver
import uk.ac.tees.mad.planty.domain.reposiotry.PlantRepository
import uk.ac.tees.mad.planty.domain.reposiotry.usecase.PlantUseCase

import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object HiltModule {

    @Provides
    @Singleton
    fun provideCoroutineScope(): CoroutineScope = CoroutineScope(Dispatchers.IO)

    @Provides
    fun provideNetworkConnectivityObserver(
        @ApplicationContext context: Context,
        scope: CoroutineScope,
    ): NetworkConnectivityObserver {
        return NetworkConnectivityObserverImpl(context, scope)

    }


    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.plant.id/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providePlantApi(retrofit: Retrofit): PlantApi {
        return retrofit.create(PlantApi::class.java)
    }


    @Provides
    fun providePlantRepository(api: PlantApi): PlantRepository {
        return PlantRepositoryImpl(
            api = api
        )
    }


    @Provides
    fun providePlantUseCase(plantRepository: PlantRepository): PlantUseCase{

        return PlantUseCase(
            plantRepository = plantRepository
        )


    }

}