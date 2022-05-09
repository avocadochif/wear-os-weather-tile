package com.avocadochif.wear.weather.di.modules

import com.avocadochif.wear.weather.BuildConfig
import com.avocadochif.wear.weather.di.annotations.AcceptInterceptor
import com.avocadochif.wear.weather.di.annotations.ContentTypeInterceptor
import com.avocadochif.wear.weather.networking.consts.NetworkingGeneralConst.ACCEPT_HEADER
import com.avocadochif.wear.weather.networking.consts.NetworkingGeneralConst.ACCEPT_HEADER_NAME
import com.avocadochif.wear.weather.networking.consts.NetworkingGeneralConst.CONNECTION_TIMEOUT
import com.avocadochif.wear.weather.networking.consts.NetworkingGeneralConst.CONTENT_TYPE_HEADER
import com.avocadochif.wear.weather.networking.consts.NetworkingGeneralConst.CONTENT_TYPE_HEADER_NAME
import com.avocadochif.wear.weather.networking.consts.NetworkingGeneralConst.READ_WRITE_TIMEOUT
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit.MILLISECONDS
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    ////////////////////////////////////////////////////////////
    ///////////////////////// RETROFIT /////////////////////////
    ////////////////////////////////////////////////////////////

    @Provides
    @Singleton
    fun provideRetrofitInstance(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory,
        coroutineCallAdapterFactory: CoroutineCallAdapterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
//            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(coroutineCallAdapterFactory)
            .build()
    }

    ////////////////////////////////////////////////////////////
    ////////////////////////// OKHTTP //////////////////////////
    ////////////////////////////////////////////////////////////

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @AcceptInterceptor acceptInterceptor: Interceptor,
        @ContentTypeInterceptor contentTypeInterceptor: Interceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(CONNECTION_TIMEOUT, MILLISECONDS)
            .readTimeout(READ_WRITE_TIMEOUT, MILLISECONDS)
            .writeTimeout(READ_WRITE_TIMEOUT, MILLISECONDS)
            .addInterceptor(acceptInterceptor)
            .addInterceptor(contentTypeInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    ////////////////////////////////////////////////////////////
    /////////////////////// INTERCEPTORS ///////////////////////
    ////////////////////////////////////////////////////////////

    @Provides
    @Singleton
    @AcceptInterceptor
    fun provideAcceptInterceptor(): Interceptor {
        return Interceptor { chain ->
            val requestBuilder = chain.request().newBuilder()
            requestBuilder.header(ACCEPT_HEADER_NAME, ACCEPT_HEADER)
            chain.proceed(requestBuilder.build())
        }
    }

    @Provides
    @Singleton
    @ContentTypeInterceptor
    fun provideContentTypeInterceptor(): Interceptor {
        return Interceptor { chain ->
            val requestBuilder = chain.request().newBuilder()
            requestBuilder.header(CONTENT_TYPE_HEADER_NAME, CONTENT_TYPE_HEADER)
            chain.proceed(requestBuilder.build())
        }
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return when (BuildConfig.DEBUG) {
            true -> HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
            false -> HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.NONE }
        }
    }

    ////////////////////////////////////////////////////////////
    ////////////////////////// OTHER ///////////////////////////
    ////////////////////////////////////////////////////////////

    @Provides
    @Singleton
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }

    @Provides
    @Singleton
    fun provideCoroutineCallAdapterFactory(): CoroutineCallAdapterFactory {
        return CoroutineCallAdapterFactory()
    }

}