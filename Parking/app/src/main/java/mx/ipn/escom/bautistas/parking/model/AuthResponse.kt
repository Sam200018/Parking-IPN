package mx.ipn.escom.bautistas.parking.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val token:String,
    @SerializedName("cuenta")
    val account: Cuenta,
)
