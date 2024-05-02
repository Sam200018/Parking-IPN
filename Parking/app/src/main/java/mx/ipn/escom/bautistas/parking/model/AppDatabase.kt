package mx.ipn.escom.bautistas.parking.model

import androidx.room.Database
import androidx.room.RoomDatabase
import mx.ipn.escom.bautistas.parking.data.token.UserDao


@Database(entities = [UserToken::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}