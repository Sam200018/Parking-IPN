package mx.ipn.escom.bautistas.parking.ui.main.viewmodels

import android.graphics.Bitmap
import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import mx.ipn.escom.bautistas.parking.data.user.UserRepository
import mx.ipn.escom.bautistas.parking.data.user.createPartFromBitmap
import mx.ipn.escom.bautistas.parking.data.user.createPartFromString
import mx.ipn.escom.bautistas.parking.model.CreateUserResponse
import mx.ipn.escom.bautistas.parking.model.Persona
import mx.ipn.escom.bautistas.parking.ui.main.interactions.NewUserState
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class NewUserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _newUserUIState = MutableStateFlow(NewUserState())
    val newUserUIState = _newUserUIState.asStateFlow()

    var nameVal: String by mutableStateOf("")
        private set

    var pLastNameVal: String by mutableStateOf("")
        private set

    var mLastNameVal: String by mutableStateOf("")
        private set

    var phoneVal: String by mutableStateOf("")
        private set

    var personPhoto: Bitmap? by mutableStateOf(null)
        private set

    var identificationPhoto: Bitmap? by mutableStateOf(null)
        private set

    var personPhotoRoute: String by mutableStateOf("")

    var identificationPhotoRoute: String by mutableStateOf("")

    fun onNameChange(name: String) {
        nameVal = name
        _newUserUIState.update {
            it.copy(isError = false, isNameValid = nameVal.isNotBlank())
        }
    }

    fun onPLastNameChange(lastNameP: String) {
        pLastNameVal = lastNameP
        _newUserUIState.update {
            it.copy(isError = false, isPLastNameValid = pLastNameVal.isNotBlank())
        }
    }

    fun onMLastNameChange(lastNameM: String) {
        mLastNameVal = lastNameM
        _newUserUIState.update {
            it.copy(isError = false, isMLastNameValid = mLastNameVal.isNotBlank())
        }
    }

    fun onPhoneChange(phone: String) {
        phoneVal = phone
        _newUserUIState.update {
            it.copy(
                isError = false,
                isPhoneValid = Patterns.PHONE.matcher(phoneVal).matches().and(phoneVal.length >= 10)
            )
        }
    }

    fun onPersonPhotoChange(photo: Bitmap?) {
        personPhoto = photo
        personPhoto?.let {
            _newUserUIState.update {
                it.copy(isError = false, isPersonPhotoTaken = true)
            }
        }
    }

    fun onIdentificationPhotoChange(photo: Bitmap?) {
        identificationPhoto = photo
        identificationPhoto?.let {
            _newUserUIState.update {
                it.copy(isError = false, isIdentificationPhotoTaken = true)
            }
        }
    }

    private suspend fun createUser(
        nombre: RequestBody,
        aPaterno: RequestBody,
        aMaterno: RequestBody,
        numeroContacto: RequestBody,
        identification: MultipartBody.Part,
        photography: MultipartBody.Part
    ): Result<CreateUserResponse> {
        return try {
            val response = userRepository.createUser(
                nombre = nombre,
                aPaterno = aPaterno,
                aMaterno = aMaterno,
                numeroContacto = numeroContacto,
                identification = identification,
                photography = photography,
                idIPN = null
            )

            Result.success(response)
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            _newUserUIState.update {
                it.copy(
                    isError = true,
                    isLoading = false,
                    message = errorBody ?: "Error desconocido"
                )
            }
            Result.failure(e)
        } catch (e: IOException) {
            _newUserUIState.update {
                it.copy(
                    isError = true,
                    isLoading = false,
                    message = e.message ?: "Error desconocido"
                )
            }
            Result.failure(e)
        }
    }

    fun onCreateNewUser() {
        _newUserUIState.update {
            it.copy(
                isLoading = true
            )
        }


        viewModelScope.launch {
            val usuarioData = Persona(
                nombre = nameVal,
                aPaterno = pLastNameVal,
                aMaterno = mLastNameVal,
                idIpn = null,
                rutaFotografia = nameVal + phoneVal,
                rutaIdentificacion = nameVal + phoneVal,
                numeroContacto = phoneVal
            )

            val nombre = createPartFromString(usuarioData.nombre)
            val aPaterno = createPartFromString(usuarioData.aPaterno)
            val aMaterno = createPartFromString(usuarioData.aMaterno)
            val numeroContacto = createPartFromString(usuarioData.numeroContacto)
            val identification =
                createPartFromBitmap(identificationPhoto!!, "identificacion")
            val photography =
                createPartFromBitmap(personPhoto!!, "fotografia")

            val result = createUser(
                nombre!!,
                aPaterno!!,
                aMaterno!!,
                numeroContacto!!, identification, photography,
            )

            if (result.isSuccess) {
                _newUserUIState.update {
                    it.copy(
                        isSuccess = true,
                        isLoading = false,
                        message = "¡Usuario creado exitosamente!"
                    )
                }
            }
        }
    }

}
