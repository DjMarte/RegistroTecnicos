package edu.ucne.registrotecnicos.data.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.ucne.registrotecnicos.data.remote.ArticuloManagerApi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ApiModule {
    const val Base_URL = "https://djcristopherarticulosapi.somee.com/swagger/v1"

    @Provides
    @Singleton
    fun ProvideMoshi(): Moshi =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    @Provides
    @Singleton
    fun ProvideArticulosApi(moshi: Moshi): ArticuloManagerApi {
        return Retrofit.Builder()
            .baseUrl(Base_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(ArticuloManagerApi::class.java)
    }
}