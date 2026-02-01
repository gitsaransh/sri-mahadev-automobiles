package com.reelrewards.app.di

import com.reelrewards.app.data.remote.ReelRewardsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideReelRewardsApi(): ReelRewardsApi {
        return Retrofit.Builder()
            .baseUrl("http://192.168.1.8:5000/") // Local Machine IP
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ReelRewardsApi::class.java)
    }
}
