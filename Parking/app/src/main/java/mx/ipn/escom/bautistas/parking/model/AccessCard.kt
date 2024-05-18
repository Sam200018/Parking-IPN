package mx.ipn.escom.bautistas.parking.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class AccessCard(
    @SerializedName("id_tarjeta_acceso")
    val idTarjetaAcceso: Long,
    @SerializedName("id_cuenta")
    val idCuenta: Long,
    @SerializedName("id_vehiculo")
    val idVehiculo: Long,
    val cuenta: Cuenta,
    val vehiculo: Vehicle,
    val token: String,
)
