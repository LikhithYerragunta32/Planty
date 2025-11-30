package uk.ac.tees.mad.planty.di


import android.app.Application
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext

import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uk.ac.tees.mad.planty.data.local.AppDatabase
import uk.ac.tees.mad.planty.data.local.PlantDao
import uk.ac.tees.mad.planty.data.remote.api.PlantApi
import uk.ac.tees.mad.planty.data.remote.api.PlantDetailApi
import uk.ac.tees.mad.planty.data.remote.api.TrefleApi
import uk.ac.tees.mad.planty.data.repoImpl.NetworkConnectivityObserverImpl
import uk.ac.tees.mad.planty.data.repoImpl.PlantRepositoryImpl
import uk.ac.tees.mad.planty.domain.reposiotry.NetworkConnectivityObserver
import uk.ac.tees.mad.planty.domain.reposiotry.PlantRepository
import uk.ac.tees.mad.planty.domain.usecase.PlantUseCase
import uk.ac.tees.mad.planty.domain.usecase.TreflePlantDetailsUseCase
import uk.ac.tees.mad.planty.domain.usecase.TrefleUseCase
import javax.inject.Named

import javax.inject.Singleton
import kotlin.jvm.java


@Module
@InstallIn(SingletonComponent::class)
object HiltModule {

    @Provides
    @Singleton
    fun provideCoroutineScope(): CoroutineScope = CoroutineScope(Dispatchers.IO)

    @Provides
    @Singleton
    fun provideNetworkConnectivityObserver(
        @ApplicationContext context: Context,
        scope: CoroutineScope,
    ): NetworkConnectivityObserver {
        return NetworkConnectivityObserverImpl(context, scope)

    }


    @Provides
    @Singleton
    @Named("plantApi")
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.plant.id/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @Named("TrefleApi")
    fun provideRetrofit2(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://trefle.io/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providePlantApi( @Named("plantApi") retrofit: Retrofit): PlantApi {
        return retrofit.create(PlantApi::class.java)
    }

    @Provides
    @Singleton
    fun provideTrefleApi( @Named("TrefleApi") retrofit: Retrofit): TrefleApi {
        return retrofit.create(TrefleApi::class.java)
    }

    @Provides
    @Singleton
    fun provideTreflePlantDetail( @Named("TrefleApi") retrofit: Retrofit): PlantDetailApi {
        return retrofit.create(PlantDetailApi::class.java)
    }



    @Provides
    @Singleton
    fun providePlantRepository(api: PlantApi,trefleApi: TrefleApi,plantDetailApi: PlantDetailApi): PlantRepository {
        return PlantRepositoryImpl(
            api = api,
            trefleApi = trefleApi,
            plantDetailApi = plantDetailApi,
        )
    }



    @Provides
    @Singleton
    fun providePlantUseCase(plantRepository: PlantRepository): PlantUseCase{

        return PlantUseCase(
            plantRepository = plantRepository
        )


    }

    @Provides
    @Singleton
    fun provideTrefleUseCase(plantRepository: PlantRepository): TrefleUseCase{

        return TrefleUseCase(
            plantRepository = plantRepository
        )


    }

    @Provides
    @Singleton
    fun provideTrefleDetailUseCase(plantRepository: PlantRepository): TreflePlantDetailsUseCase{

        return TreflePlantDetailsUseCase(
            plantRepository = plantRepository
        )


    }

    @Provides
    @Singleton
    fun providesDB(app: Application): AppDatabase {
        return Room.databaseBuilder(app, AppDatabase::class.java,"app_db").build()
    }

    @Provides
    fun providesDao(db: AppDatabase): PlantDao{
        return db.plantDao()
    }


}