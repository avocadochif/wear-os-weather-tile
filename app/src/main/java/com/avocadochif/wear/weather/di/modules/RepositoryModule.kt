package com.avocadochif.wear.weather.di.modules

import com.avocadochif.wear.weather.networking.OpenWeatherMapRequest
import com.avocadochif.wear.weather.networking.repository.OneCallWeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.scopes.ServiceScoped

@Module
@InstallIn(ServiceComponent::class)
object RepositoryModule {

    @Provides
    @ServiceScoped
    fun provideOneCallWeatherRepository(service: OpenWeatherMapRequest.OneCallAPI): OneCallWeatherRepository {
        return OneCallWeatherRepository(service)
    }

}