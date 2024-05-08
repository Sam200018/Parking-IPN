package mx.ipn.escom.bautistas.parking.di

import android.content.Context
import androidx.room.Room
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
import mx.ipn.escom.bautistas.parking.data.token.UserDao
import mx.ipn.escom.bautistas.parking.data.user.UserDataSource
import mx.ipn.escom.bautistas.parking.data.user.UserRepository
import mx.ipn.escom.bautistas.parking.data.user.UserRepositoryImpl
import mx.ipn.escom.bautistas.parking.data.vehicle.VehicleDataSource
import mx.ipn.escom.bautistas.parking.data.vehicle.VehicleRepository
import mx.ipn.escom.bautistas.parking.data.vehicle.VehicleRepositoryImpl
import mx.ipn.escom.bautistas.parking.model.AppDatabase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
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
// remote data sources
    @Provides
    @Singleton
    fun providesAuthDataSource(retrofit: Retrofit): AuthDataSource =
    retrofit.create(AuthDataSource::class.java)

    @Provides
    @Singleton
    fun providesUserDataSource(retrofit: Retrofit): UserDataSource =
        retrofit.create(UserDataSource::class.java)

    @Provides
    @Singleton
    fun providesVehicleDataSource(retrofit: Retrofit): VehicleDataSource =
        retrofit.create(VehicleDataSource::class.java)
//Repos
    @Provides
    @Singleton
    fun providesAuthRepository(authDataSource: AuthDataSource, userDao: UserDao): AuthRepository =
        AuthRepositoryImpl(authDataSource,userDao)


    @Provides
    @Singleton
    fun providesUserRepository(userDataSource: UserDataSource): UserRepository =
        UserRepositoryImpl(userDataSource)

    @Provides
    @Singleton
    fun providesVehicleRepository(vehicleDataSource: VehicleDataSource): VehicleRepository =
        VehicleRepositoryImpl(vehicleDataSource)

// Room

    @Provides
    @Singleton
    fun providesUserTokenRoomDatabase(@ApplicationContext app: Context): AppDatabase =
        Room.databaseBuilder(
            app, AppDatabase::class.java, "app_database"
        ).fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun providesNotesDao(appDatabase: AppDatabase): UserDao = appDatabase.userDao()
}