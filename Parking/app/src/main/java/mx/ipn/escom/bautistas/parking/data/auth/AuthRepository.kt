package mx.ipn.escom.bautistas.parking.data.auth

import mx.ipn.escom.bautistas.parking.data.token.UserDao
import mx.ipn.escom.bautistas.parking.model.AuthResponse
import mx.ipn.escom.bautistas.parking.model.LoginRequest
import mx.ipn.escom.bautistas.parking.model.UserToken
import javax.inject.Inject

interface AuthRepository {
    suspend fun login(loginRequest: LoginRequest): AuthResponse
    suspend fun getToken(): UserToken?
    suspend fun saveToken(userToken: UserToken)
    suspend fun checkStatus(token: String): AuthResponse
    suspend fun logout()
}


class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource,
    private val userDao: UserDao
) : AuthRepository {
    override suspend fun login(loginRequest: LoginRequest): AuthResponse =
        authDataSource.login(loginRequest)

    override suspend fun getToken() = userDao.getToken()
    override suspend fun saveToken(userToken: UserToken) = userDao.insertToken(userToken)
    override suspend fun checkStatus(token: String) = authDataSource.checkStatus(token)
    override suspend fun logout() = userDao.deleteAllTokens()


}