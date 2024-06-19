package mx.ipn.escom.bautistas.parking.ui.main.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import mx.ipn.escom.bautistas.parking.data.auth.AuthRepository
import mx.ipn.escom.bautistas.parking.model.AuthResponse
import mx.ipn.escom.bautistas.parking.model.LoginRequest
import mx.ipn.escom.bautistas.parking.model.UserToken
import mx.ipn.escom.bautistas.parking.ui.main.interactions.AuthState
import mx.ipn.escom.bautistas.parking.ui.main.interactions.AuthStatus
import retrofit2.HttpException
import java.io.IOException
import java.lang.RuntimeException
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    init {
        viewModelScope.launch {
            checkStatus()
        }
    }

    private val _authState = MutableStateFlow(AuthState())
    val authState = _authState.asStateFlow()

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
        Log.i("Hola", "Login")
        _authState.update {
            it.copy(
                isLoading = true
            )
        }
        viewModelScope.launch {
            login(email, password).onSuccess { res ->
                setLoggedUser(res)
            }.onFailure { err ->
                Log.e("error login", err.message.toString())
                logout(message = err.message)
            }
        }
    }

    fun logout(message: String? = null) {
        viewModelScope.launch {
            authRepository.logout()
        }

        _authState.value = AuthState(
            AuthStatus.Unauthenticated,
            message = message,
            isError = true
        )
    }

    private suspend fun checkStatus() {
        val userToken = authRepository.getToken()
        if (userToken == null) {
            logout()
        } else {
            try {
                val response = authRepository.checkStatus("Bearer ${userToken.token}")
                setLoggedUser(response)
            } catch (e: Exception) {
                logout(e.message.toString())
            }
        }
    }


    private suspend fun setLoggedUser(response: AuthResponse?) {
        val token = response!!.token
        Log.i("Token to save:", token)
        val userToken = UserToken(token = token, account = response.account.idCuenta)
        authRepository.saveToken(userToken)

        _authState.value = AuthState(
            message = "Inicio exitoso",
            authStatus = AuthStatus.Authenticated,
            account = response.account
        )
    }
}