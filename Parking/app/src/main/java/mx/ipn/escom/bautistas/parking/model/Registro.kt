package mx.ipn.escom.bautistas.parking.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Registro(
    @SerializedName("id_registro")
    val idRegistro: Long,
    @SerializedName("id_tarjeta_acceso")
    val idTarjetaAcceso: Long? = null,
    @SerializedName("id_cuenta")
    val idCuenta: Long
)
