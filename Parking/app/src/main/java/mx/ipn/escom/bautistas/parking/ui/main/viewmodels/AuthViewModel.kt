package mx.ipn.escom.bautistas.parking.ui.main.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import mx.ipn.escom.bautistas.parking.data.auth.AuthRepository
import mx.ipn.escom.bautistas.parking.model.AuthResponse
import mx.ipn.escom.bautistas.parking.model.LoginRequest
import retrofit2.HttpException
import java.io.IOException
import java.lang.RuntimeException
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {


    suspend fun login(email: String, password: String): Result<AuthResponse> {
        return try {
            val loginRequest = LoginRequest(email, password)
            val response = authRepository.login(loginRequest)
            Log.i("login r", response.toString())
            Result.success(response)
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            Result.failure(RuntimeException(errorBody ?: "Error desconocido"))
        } catch (e: IOException) {
            Result.failure(RuntimeException("Revisa tu conexion a internet"))
        }
    }

    fun doLogin(email: String, password: String) {
        viewModelScope.launch { login(email, password) }
    }
}