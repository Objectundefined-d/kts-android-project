package com.example.kts_project.di

import com.example.kts_project.data.remote.StepikApi
import com.example.kts_project.data.repository.LoginRepositoryImpl
import com.example.kts_project.data.repository.CourseRepositoryImpl
import com.example.kts_project.domain.repository.CourseRepository
import com.example.kts_project.domain.repository.LoginRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideLoginRepository(): LoginRepository = LoginRepositoryImpl()

    @Provides
    @Singleton
    fun provideCourseRepository(api: StepikApi): CourseRepository = CourseRepositoryImpl(api)
}