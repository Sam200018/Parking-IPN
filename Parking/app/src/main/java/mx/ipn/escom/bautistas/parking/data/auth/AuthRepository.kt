package mx.ipn.escom.bautistas.parking.data.auth

import mx.ipn.escom.bautistas.parking.model.AuthResponse
import mx.ipn.escom.bautistas.parking.model.LoginRequest
import javax.inject.Inject

interface AuthRepository {
    suspend fun login(loginRequest: LoginRequest): AuthResponse
}


class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource
) : AuthRepository {
    override suspend fun login(loginRequest: LoginRequest): AuthResponse =
        authDataSource.login(loginRequest)
}