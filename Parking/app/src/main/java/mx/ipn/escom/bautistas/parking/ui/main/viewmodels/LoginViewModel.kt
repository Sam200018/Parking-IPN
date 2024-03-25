package mx.ipn.escom.bautistas.parking.ui.main.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(

) : ViewModel() {
    var emailInput: String by mutableStateOf("")
        private set

    var passwordInput: String by mutableStateOf("")
        private set

    fun onEmailChange(email: String) {
        emailInput = email

    }

    fun onPasswordChange(password: String) {
        passwordInput = password
    }

    fun onPasswordVisibilityChange(){

    }
}