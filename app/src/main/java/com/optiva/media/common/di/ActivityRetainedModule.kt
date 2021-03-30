package com.optiva.media.common.di

import com.optiva.media.repository.MovieDataRepository
import com.optiva.media.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ActivityRetainedModule {
    @Binds
    abstract fun dataSource(impl: MovieDataRepository): MovieRepository
}
