package com.g3tech.paceful.di

import com.g3tech.paceful.routing.Navigator
import com.g3tech.paceful.routing.NavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NavigationModule {

    @Binds
    @Singleton
    abstract fun bindNavigator(navigator: NavigatorImpl): Navigator
}