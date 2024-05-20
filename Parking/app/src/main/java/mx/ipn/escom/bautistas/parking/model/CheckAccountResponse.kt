package mx.ipn.escom.bautistas.parking.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class CheckAccountResponse(
    val message: String,
    val verified: Boolean,
    @SerializedName("account")
    val cuenta: Cuenta? = null
)
