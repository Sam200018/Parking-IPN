package mx.ipn.escom.bautistas.parking.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_data")
data class UserToken(
    @PrimaryKey val id: Int = 1,
    @ColumnInfo(name = "token") val token: String
)