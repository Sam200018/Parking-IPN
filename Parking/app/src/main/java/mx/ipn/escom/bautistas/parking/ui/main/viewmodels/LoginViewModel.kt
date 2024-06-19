package mx.ipn.escom.bautistas.parking.ui.main.viewmodels

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import mx.ipn.escom.bautistas.parking.ui.main.interactions.LoginUiState
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(

) : ViewModel() {
    var emailInput: String by mutableStateOf("")
        private set

    var passwordInput: String by mutableStateOf("")
        private set

    private val _loginUiState = MutableStateFlow(LoginUiState())
    val loginUiState = _loginUiState

    fun onEmailChange(email: String) {
        _loginUiState.update {
            it.copy(
                isError = false,
                isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
            )
        }
        emailInput = email
    }

    fun onPasswordChange(password: String) {
        passwordInput = password
    }

    fun onPasswordVisibilityChange() {
        _loginUiState.update {
            it.copy(
                isVisiblePassword = it.isVisiblePassword.not()
            )
        }
    }
}