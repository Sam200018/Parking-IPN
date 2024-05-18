package mx.ipn.escom.bautistas.parking.data.token

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import mx.ipn.escom.bautistas.parking.model.UserToken

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToken(userToken: UserToken)

    @Query("SELECT token, account,id FROM user_data WHERE id = 1")
    suspend fun getToken(): UserToken

    @Query("DELETE FROM user_data")
    suspend fun deleteAllTokens()

}