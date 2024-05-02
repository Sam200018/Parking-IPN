package mx.ipn.escom.bautistas.parking.data.auth

import mx.ipn.escom.bautistas.parking.model.AuthResponse
import mx.ipn.escom.bautistas.parking.model.LoginRequest
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthDataSource {
    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest ): AuthResponse

    @POST("check-status")
    suspend fun checkStatus(@Header("Authorization")token:String): AuthResponse
}