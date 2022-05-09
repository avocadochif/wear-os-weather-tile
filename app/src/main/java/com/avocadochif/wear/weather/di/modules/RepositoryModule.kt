package com.avocadochif.wear.weather.di.modules

import com.avocadochif.wear.weather.networking.OpenWeatherMapRequest
import com.avocadochif.wear.weather.networking.repository.OneCallWeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideOneCallWeatherRepository(service: OpenWeatherMapRequest.OneCallAPI): OneCallWeatherRepository {
        return OneCallWeatherRepository(service)
    }

}