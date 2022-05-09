package com.avocadochif.wear.weather.di.modules

import com.avocadochif.wear.weather.networking.OpenWeatherMapRequest
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object ServiceModule {

    @Provides
    @ViewModelScoped
    fun provideOneCallAPIService(retrofit: Retrofit): OpenWeatherMapRequest.OneCallAPI {
        return retrofit.create(OpenWeatherMapRequest.OneCallAPI::class.java)
    }

}