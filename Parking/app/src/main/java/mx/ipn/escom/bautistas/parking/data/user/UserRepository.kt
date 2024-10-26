package mx.ipn.escom.bautistas.parking.data.user


import mx.ipn.escom.bautistas.parking.model.CheckAccountResponse
import mx.ipn.escom.bautistas.parking.model.CreateAccountRequest
import mx.ipn.escom.bautistas.parking.model.CreateAccountResponse
import mx.ipn.escom.bautistas.parking.model.CreateUserResponse
import mx.ipn.escom.bautistas.parking.model.GetAccountsResponse
import mx.ipn.escom.bautistas.parking.model.GetPeopleResponse
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

    suspend fun getPeople(input: String? = null): GetPeopleResponse

    suspend fun checkAccountByPersona(idPersona: Long): CheckAccountResponse

    suspend fun getPersonaById(idPersona: Long): CreateUserResponse

    suspend fun getAllAccounts(): GetAccountsResponse
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

    override suspend fun getPeople(input: String?): GetPeopleResponse =
        userDataSource.getPeople(input = input)

    override suspend fun checkAccountByPersona(idPersona: Long) =
        userDataSource.checkAccountByPersona(idPersona)

    override suspend fun getPersonaById(idPersona: Long): CreateUserResponse =
        userDataSource.getPersonaById(
            idPersona
        )

    override suspend fun getAllAccounts(): GetAccountsResponse = userDataSource.getAllAccounts()
}