package mx.ipn.escom.bautistas.parking.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Registro(
    @SerializedName("id_registro")
    val idRegistro: Long,
    @SerializedName("id_token")
    val idToken: Any? = null,
    val cuenta: Cuenta,
    @SerializedName("tarjeta_acceso")
    val tarjetaAcceso: AccessCard? = null,
    val visita: Visit? = null

)
