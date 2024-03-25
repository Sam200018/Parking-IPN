package mx.ipn.escom.bautistas.parking.di

import android.content.Context
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import mx.ipn.escom.bautistas.parking.config.PropertiesManager
import mx.ipn.escom.bautistas.parking.data.auth.AuthDataSource
import mx.ipn.escom.bautistas.parking.data.auth.AuthRepository
import mx.ipn.escom.bautistas.parking.data.auth.AuthRepositoryImpl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    //    properties
    @Provides
    @Singleton
    fun provideProperties(@ApplicationContext context: Context): PropertiesManager =
        PropertiesManager(context)


    val gson = GsonBuilder()
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
        .create()

    @Provides
    @Singleton
    fun providesRetrofit(propertiesManager: PropertiesManager): Retrofit {

        val baseUrl = propertiesManager.getString("BASE_URL") ?: "http://192.168.10.105:8000/api/"
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson)).baseUrl(baseUrl).build()
    }

    @Provides
    @Singleton
    fun providesAuthDataSource(retrofit: Retrofit): AuthDataSource {
        return retrofit.create(AuthDataSource::class.java)
    }

    @Provides
    @Singleton
    fun providesAuthRepository(authDataSource: AuthDataSource): AuthRepository =
        AuthRepositoryImpl(authDataSource)
}