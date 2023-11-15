package com.ikozlu.capstone_mitemauto.di

import com.google.firebase.auth.FirebaseAuth
import com.ikozlu.capstone_mitemauto.data.repository.AuthRepo
import com.ikozlu.capstone_mitemauto.data.repository.AuthRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun provideAuthRepository(impl: AuthRepoImpl): AuthRepo = impl
}