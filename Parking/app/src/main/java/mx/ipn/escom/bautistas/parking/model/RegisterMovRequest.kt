package mx.ipn.escom.bautistas.parking.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterMovRequest(
    @SerializedName("id_cuenta")
    val idCuenta:Long,
    @SerializedName("id_tarjeta_acceso")
    val idTarjetaAcceso: Long? = null,
    @SerializedName("id_visita")
    val idVisita: Long? = null,
    @SerializedName("movimiento")
    val movement: Int,
)
