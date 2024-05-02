package mx.ipn.escom.bautistas.parking.data.user


import mx.ipn.escom.bautistas.parking.model.CreateAccountRequest
import mx.ipn.escom.bautistas.parking.model.CreateAccountResponse
import mx.ipn.escom.bautistas.parking.model.CreateUserResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

interface UserRepository {
    suspend fun createUser(
        nombre: RequestBody,
        aPaterno: RequestBody,
        aMaterno: RequestBody,
        idIPN: RequestBody?,
        numeroContacto: RequestBody,
        identification: MultipartBody.Part,
        photography: MultipartBody.Part,
    ): CreateUserResponse

    suspend fun createAccount(createAccountRequest: CreateAccountRequest): CreateAccountResponse
}

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource
) : UserRepository {

    override suspend fun createUser(
        nombre: RequestBody,
        aPaterno: RequestBody,
        aMaterno: RequestBody,
        idIPN: RequestBody?,
        numeroContacto: RequestBody,
        identification: MultipartBody.Part,
        photography: MultipartBody.Part
    ): CreateUserResponse = userDataSource.createUser(
        nombre,
        aPaterno,
        aMaterno,
        idIPN,
        numeroContacto,
        identification,
        photography
    )

    override suspend fun createAccount(createAccountRequest: CreateAccountRequest): CreateAccountResponse =
        userDataSource.createAccount(createAccountRequest)

}