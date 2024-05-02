package mx.ipn.escom.bautistas.parking.ui.main.viewmodels

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import mx.ipn.escom.bautistas.parking.data.user.UserRepository
import mx.ipn.escom.bautistas.parking.data.user.createPartFromBitmap
import mx.ipn.escom.bautistas.parking.data.user.createPartFromString
import mx.ipn.escom.bautistas.parking.model.CreateAccountRequest
import mx.ipn.escom.bautistas.parking.model.CreateAccountResponse
import mx.ipn.escom.bautistas.parking.model.CreateUserResponse
import mx.ipn.escom.bautistas.parking.model.Persona
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class NewUserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    var userTypeVal: Int by mutableStateOf(1)
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

    fun onUserTypeChanged(type: Int) {
        userTypeVal = type
    }

    fun onIpnIDChanged(ipnIDval: String?) {
        ipnIDVal = ipnIDval
    }

    fun onProgAcademicoChange(prog: Int?) {
        progAcademicoVal = prog
    }

    fun onNameChange(name: String) {
        nameVal = name
    }

    fun onPLastNameChange(lastNameP: String) {
        pLastNameVal = lastNameP
    }

    fun onMLastNameChange(lastNameM: String) {
        mLastNameVal = lastNameM
    }

    fun onPhoneChange(phone: String) {
        phoneVal = phone
    }

    fun onEmailChange(email: String) {
        emailVal = email
    }

    fun onPersonPhotoChange(photo: Bitmap?) {
        personPhoto = photo
    }

    fun onIdentificationPhotoChange(photo: Bitmap?) {
        identificationPhoto = photo
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
            Result.failure(RuntimeException(errorBody ?: "Error desconocido"))
        } catch (e: IOException) {
            Result.failure(RuntimeException("Error desconocido"))
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
            Result.failure(RuntimeException(errorBody ?: "Error desconocido"))
        } catch (e: IOException) {
            Result.failure(RuntimeException("Error desconocido"))
        }
    }


    fun onNewUserCreated() {
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
                Log.i("Exito", "exito")
                val response = result.getOrDefault(null)
                val createAccountRequest = CreateAccountRequest(
                    response!!.user.idPersona,
                    userTypeVal,
                    progAcademicoVal,
                    emailVal
                )
                val resultCreateAccount = createAccount(createAccountRequest)
                if (resultCreateAccount.isSuccess) {
                    Log.i("Exitoso", "Crate Account")
                    val resp = resultCreateAccount.getOrDefault(null)
                    resp?.tempPassword?.let { Log.i("Temp password", it) }
                } else {
                    val error = result.exceptionOrNull()?.message ?: "jeje"
                    Log.e("NO Exito", error)
                }
            } else {
                val error = result.exceptionOrNull()?.message ?: "jeje"
                Log.e("NO Exito", error)

            }
        }
    }


}