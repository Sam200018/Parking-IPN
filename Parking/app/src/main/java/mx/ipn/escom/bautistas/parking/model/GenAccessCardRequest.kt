package mx.ipn.escom.bautistas.parking.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class GenAccessCardRequest(
    @SerializedName("id_cuenta")
    val idCuenta: Long,
    @SerializedName("id_vehiculo")
    val idVehicle: Long,
)
