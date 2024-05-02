package mx.ipn.escom.bautistas.parking.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class CreateUserResponse(
    val message: String,
    @SerializedName("usuario")
    val user: Persona
)
