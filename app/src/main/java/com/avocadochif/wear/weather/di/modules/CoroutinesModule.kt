package com.avocadochif.wear.weather.di.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.scopes.ServiceScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(ServiceComponent::class)
object CoroutinesModule {

    @Provides
    @ServiceScoped
    fun provideServiceScope(): CoroutineScope {
        return CoroutineScope(Dispatchers.IO)
    }

}