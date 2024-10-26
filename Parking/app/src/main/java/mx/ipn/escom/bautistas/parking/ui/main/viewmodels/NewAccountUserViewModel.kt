package mx.ipn.escom.bautistas.parking.ui.main.viewmodels

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
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
import mx.ipn.escom.bautistas.parking.model.CreateAccountRequest
import mx.ipn.escom.bautistas.parking.model.CreateAccountResponse
import mx.ipn.escom.bautistas.parking.model.CreateUserResponse
import mx.ipn.escom.bautistas.parking.model.Persona
import mx.ipn.escom.bautistas.parking.ui.main.interactions.NewAccountUserState
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import java.io.IOException
import java.io.InputStream
import javax.inject.Inject

@HiltViewModel
class NewAccountUserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _newAccountUserUIState = MutableStateFlow(NewAccountUserState())
    val newAccountUserUiState = _newAccountUserUIState.asStateFlow()


    var userTypeVal: Int by mutableStateOf(0)
        private set

    var ipnIDVal: String? by mutableStateOf(null)
        private set

    var progAcademicoVal: Int? by mutableStateOf(null)
        private set

    var nameVal: String by mutableStateOf("")
        private set

    var pLastNameVal: String by mutableStateOf("")
        private set

    var mLastNameVal: String by mutableStateOf("")
        private set

    var phoneVal: String by mutableStateOf("")
        private set

    var emailVal: String by mutableStateOf("")
        private set

    var personPhoto: Bitmap? by mutableStateOf(null)
        private set
    var identificationPhoto: Bitmap? by mutableStateOf(null)
        private set

    var personPhotoRoute: String by mutableStateOf("")

    var identificationPhotoRoute: String by mutableStateOf("")

    fun onUserTypeChanged(type: Int) {
        userTypeVal = type
        if (userTypeVal == 0) {
            _newAccountUserUIState.update {
                it.copy(isError = false, isTypeUserSelected = false)
            }

        } else {
            if (userTypeVal == 2) {

                _newAccountUserUIState.update {
                    it.copy(
                        isError = false,
                        isTypeUserSelected = true,
                        isAcademProgSelected = false
                    )
                }
            } else {
                if (userTypeVal == 5) {
                    ipnIDVal = null
                }
                _newAccountUserUIState.update {
                    it.copy(isError = false, isTypeUserSelected = true, isAcademProgSelected = true)
                }
                progAcademicoVal = null
            }
        }
    }

    fun onIpnIDChanged(ipnIDval: String?) {
        ipnIDVal = ipnIDval
        ipnIDval?.let {
            _newAccountUserUIState.update {
                it.copy(
                    isError = false,
                    isIPNIdValid = ipnIDval.isNotEmpty().and(ipnIDval.length > 4)
                )
            }
        }
    }

    fun onProgAcademicoChange(prog: Int?) {
        progAcademicoVal = prog
        prog?.let {
            _newAccountUserUIState.update {
                it.copy(isError = false, isAcademProgSelected = prog != 0)
            }
        }
    }

    fun onNameChange(name: String) {
        nameVal = name
        _newAccountUserUIState.update {
            it.copy(isError = false, isNameValid = nameVal.isNotBlank())
        }
    }

    fun onPLastNameChange(lastNameP: String) {
        pLastNameVal = lastNameP
        _newAccountUserUIState.update {
            it.copy(isError = false, isPLastNameValid = pLastNameVal.isNotBlank())
        }
    }

    fun onMLastNameChange(lastNameM: String) {
        mLastNameVal = lastNameM
        _newAccountUserUIState.update {
            it.copy(isError = false, isMLastNameValid = mLastNameVal.isNotBlank())
        }
    }

    fun onPhoneChange(phone: String) {
        phoneVal = phone
        _newAccountUserUIState.update {
            it.copy(
                isError = false,
                isPhoneValid = Patterns.PHONE.matcher(phoneVal).matches().and(phoneVal.length >= 10)
            )
        }
    }

    fun onEmailChange(email: String) {
        emailVal = email
        _newAccountUserUIState.update {
            it.copy(
                isError = false,
                isEmailValid = Patterns.EMAIL_ADDRESS.matcher(emailVal).matches()
            )
        }
    }

    fun onPersonPhotoChange(photo: Bitmap?) {
        personPhoto = photo
        personPhoto?.let {
            _newAccountUserUIState.update {
                it.copy(isError = false, isPersonPhotoTaken = true)
            }
        }
    }

    fun onIdentificationPhotoChange(photo: Uri?, context: Context) {
        _newAccountUserUIState.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
            var inputStream: InputStream? = null
            try {
                inputStream = context.contentResolver.openInputStream(photo!!)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                identificationPhoto = bitmap
                identificationPhoto?.let {
                    _newAccountUserUIState.update {
                        it.copy(
                            isError = false,
                            isLoading = false,
                            isIdentificationPhotoTaken = true
                        )
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                inputStream?.close()
            }
        }
    }

    private suspend fun createUser(
        nombre: RequestBody,
        aPaterno: RequestBody,
        aMaterno: RequestBody,
        idIPN: RequestBody?,
        numeroContacto: RequestBody,
        identification: MultipartBody.Part,
        photography: MultipartBody.Part
    ): Result<CreateUserResponse> {
        return try {
            val response = userRepository.createUser(
                nombre,
                aPaterno,
                aMaterno,
                idIPN,
                numeroContacto, identification, photography,
            )

            Result.success(response)
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            _newAccountUserUIState.update {
                it.copy(
                    isError = true,
                    isLoading = false,
                    message = errorBody ?: "Error desconocido"
                )
            }
            Result.failure(e)
        } catch (e: IOException) {
            _newAccountUserUIState.update {
                it.copy(
                    isError = true,
                    isLoading = false,
                    message = e.message ?: "Error desconocido"
                )
            }
            Result.failure(e)
        }

    }

    private suspend fun createAccount(
        createAccountRequest: CreateAccountRequest
    ): Result<CreateAccountResponse> {
        return try {
            val response = userRepository.createAccount(createAccountRequest)
            Result.success(response)
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            _newAccountUserUIState.update {
                it.copy(
                    isError = true,
                    isLoading = false,
                    message = errorBody ?: "Error desconocido"
                )
            }
            Result.failure(e)
        } catch (e: IOException) {

            _newAccountUserUIState.update {
                it.copy(
                    isError = true,
                    isLoading = false,
                    message = e.message ?: "Error desconocido"
                )
            }
            Result.failure(e)
        }
    }


    fun onNewAccountUserCreated() {
        _newAccountUserUIState.update {
            it.copy(isLoading = true)
        }

        viewModelScope.launch {

            val usuarioData = Persona(
                nombre = nameVal,
                aPaterno = pLastNameVal,
                aMaterno = mLastNameVal,
                idIpn = ipnIDVal,
                rutaFotografia = nameVal + phoneVal,
                rutaIdentificacion = nameVal + phoneVal,
                numeroContacto = phoneVal,

                )


            val nombre = createPartFromString(usuarioData.nombre)
            val aPaterno = createPartFromString(usuarioData.aPaterno)
            val aMaterno = createPartFromString(usuarioData.aMaterno)
            val idIpn = createPartFromString(usuarioData.idIpn)
            val numeroContacto = createPartFromString(usuarioData.numeroContacto)

            val identification =
                createPartFromBitmap(identificationPhoto!!, "identificacion")
            val photography =
                createPartFromBitmap(personPhoto!!, "fotografia")


            val result = createUser(
                nombre!!,
                aPaterno!!,
                aMaterno!!,
                idIpn,
                numeroContacto!!, identification, photography
            )
            if (result.isSuccess) {
                val response = result.getOrDefault(null)
                val createAccountRequest = CreateAccountRequest(
                    response!!.user.idPersona,
                    userTypeVal,
                    progAcademicoVal,
                    emailVal
                )
                val resultCreateAccount = createAccount(createAccountRequest)
                if (resultCreateAccount.isSuccess) {
                    val resp = resultCreateAccount.getOrDefault(null)
                    resp?.tempPassword?.let { pass ->
                        Log.i("Temp password", pass)
                        _newAccountUserUIState.update {
                            it.copy(
                                isSuccess = true,
                                isLoading = false,
                                message = "Contraseña: $pass"
                            )
                        }
                    }
                }
            }
        }
    }

    fun loadPersonaInfo(idPersona: Long) {
        Log.i("Persona id", idPersona.toString())
        _newAccountUserUIState.update {
            it.copy(
                isLoading = true
            )
        }
        viewModelScope.launch {

            getInfoById(idPersona).onSuccess { resp ->
                val persona = resp.user

                ipnIDVal = persona.idIpn
                nameVal = persona.nombre
                pLastNameVal = persona.aPaterno
                mLastNameVal = persona.aMaterno
                phoneVal = persona.numeroContacto
                personPhotoRoute = persona.rutaFotografia
                identificationPhotoRoute = persona.rutaIdentificacion

                _newAccountUserUIState.update {
                    NewAccountUserState(
                        isEmailValid = false,
                        isPersonPhotoTaken = true,
                        isIdentificationPhotoTaken = true,
                    )
                }


            }.onFailure { error ->
                _newAccountUserUIState.update {
                    NewAccountUserState(
                        isError = true,
                        message = error.message ?: "Error desconocido"
                    )
                }
            }

        }

    }

    private suspend fun getInfoById(idPersona: Long): Result<CreateUserResponse> {
        return try {
            val resp = userRepository.getPersonaById(idPersona)
            Result.success(resp)
        } catch (e: HttpException) {
            Result.failure(e)
        } catch (e: IOException) {
            Result.failure(e)
        }
    }

    fun onCreateAccount(idPersona: Long) {
        _newAccountUserUIState.update {
            it.copy(
                isLoading = true
            )
        }
        viewModelScope.launch {
            val createAccountRequest = CreateAccountRequest(
                idPersona,
                userTypeVal,
                progAcademicoVal,
                emailVal
            )
            val resultCreateAccount = createAccount(createAccountRequest)
            resultCreateAccount.onSuccess { resp ->

                _newAccountUserUIState.update {
                    it.copy(
                        isSuccess = true,
                        isLoading = false,
                        message = "Contraseña: ${resp.tempPassword}"
                    )
                }
            }
        }
    }
}