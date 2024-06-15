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
import mx.ipn.escom.bautistas.parking.data.card.AccessCardDataSource
import mx.ipn.escom.bautistas.parking.data.card.AccessCardRepository
import mx.ipn.escom.bautistas.parking.data.card.AccessCardRepositoryImpl
import mx.ipn.escom.bautistas.parking.data.card.CardLocalSource
import mx.ipn.escom.bautistas.parking.data.pusher.PusherManager
import mx.ipn.escom.bautistas.parking.data.records.RecordsDataSource
import mx.ipn.escom.bautistas.parking.data.records.RecordsRepository
import mx.ipn.escom.bautistas.parking.data.records.RecordsRepositoryImpl
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

    @Provides
    @Singleton
    fun providesAccessCardDataSource(retrofit: Retrofit): AccessCardDataSource =
        retrofit.create(AccessCardDataSource::class.java)

    @Provides
    @Singleton
    fun providesRecordsDataSource(retrofit: Retrofit): RecordsDataSource =
        retrofit.create(RecordsDataSource::class.java)

    //Repos
    @Provides
    @Singleton
    fun providesAuthRepository(authDataSource: AuthDataSource, userDao: UserDao): AuthRepository =
        AuthRepositoryImpl(authDataSource, userDao)


    @Provides
    @Singleton
    fun providesUserRepository(userDataSource: UserDataSource): UserRepository =
        UserRepositoryImpl(userDataSource)

    @Provides
    @Singleton
    fun providesVehicleRepository(vehicleDataSource: VehicleDataSource): VehicleRepository =
        VehicleRepositoryImpl(vehicleDataSource)

    @Provides
    @Singleton
    fun providesAccessCardRepository(
        accessCardDataSource: AccessCardDataSource,
        cardLocalSource: CardLocalSource,
        pusherManager: PusherManager
    ): AccessCardRepository =
        AccessCardRepositoryImpl(accessCardDataSource, cardLocalSource, pusherManager)

    @Provides
    @Singleton
    fun providesRecordsRepository(
        recordsDataSource: RecordsDataSource,
        pusherManager: PusherManager
    ): RecordsRepository = RecordsRepositoryImpl(recordsDataSource, pusherManager)

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

    //    Shared Preferences
    @Provides
    @Singleton
    fun providePreferencesManager(@ApplicationContext context: Context): CardLocalSource {
        return CardLocalSource(context)
    }

    //    Pusher
    @Provides
    @Singleton
    fun providesPusherManager(propertiesManager: PropertiesManager): PusherManager {
        val pusherKey = propertiesManager.getString("PUSHER_APP_KEY") ?: ""
        val pusherCluster = propertiesManager.getString("PUSHER_APP_CLUSTER") ?: ""
        return PusherManager(pusherKey, pusherCluster)
    }
}