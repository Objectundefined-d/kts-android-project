package com.example.kts_project.di

import coil3.network.NetworkClient
import com.example.kts_project.data.remote.KtorClient
import com.example.kts_project.data.remote.StepikApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient = KtorClient.httpClient

    @Provides
    @Singleton
    fun provideStepikApi(client: HttpClient): StepikApi = StepikApi(client)
}