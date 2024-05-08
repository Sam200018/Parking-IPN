package mx.ipn.escom.bautistas.parking.data.user

import android.graphics.Bitmap
import mx.ipn.escom.bautistas.parking.model.CreateAccountRequest
import mx.ipn.escom.bautistas.parking.model.CreateAccountResponse
import mx.ipn.escom.bautistas.parking.model.CreateUserResponse
import mx.ipn.escom.bautistas.parking.model.GetPeopleResponse
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query
import java.io.ByteArrayOutputStream

interface UserDataSource {
    @Multipart
    @POST("create_user")
    suspend fun createUser(
        @Part("nombre") nombre: RequestBody,
        @Part("a_paterno") aPaterno: RequestBody,
        @Part("a_materno") aMaterno: RequestBody,
        @Part("id_ipn") idIPN: RequestBody?,
        @Part("numero_contacto") numeroContacto: RequestBody,
        @Part identificacion: MultipartBody.Part,
        @Part fotografia: MultipartBody.Part,
    ): CreateUserResponse

    @POST("create_account")
    suspend fun createAccount(@Body createAccountRequest: CreateAccountRequest): CreateAccountResponse

    @GET("get_all_users")
    suspend fun getPeople(@Query("input") input: String? = null): GetPeopleResponse

}

fun createPartFromBitmap(bitmap: Bitmap, partName: String): MultipartBody.Part {
    val byteArrayOutputStream = ByteArrayOutputStream()

    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream)
    val byteArray = byteArrayOutputStream.toByteArray()

    val requestBody = byteArray.toRequestBody("image/jpeg".toMediaType())
    val filename = "image_${System.currentTimeMillis()}.jpg"

    return MultipartBody.Part.createFormData(partName, filename, requestBody)
}

fun createPartFromString(value: String?): RequestBody? {
    return value?.toRequestBody(MultipartBody.FORM)
}
