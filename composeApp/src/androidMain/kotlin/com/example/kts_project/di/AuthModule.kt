package com.example.kts_project.di

import android.content.Context
import com.example.kts_project.data.auth.AuthService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AuthModule {

    @Provides
    @Singleton
    fun provideAuthService(@ApplicationContext context: Context) : AuthService {
        return AuthService(context)
    }

}