package mx.ipn.escom.bautistas.parking.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class InfoRecordRequest (
    @SerializedName("id_persona")
    val personId: Long,
    @SerializedName("id_vehiculo")
    val vehicleId: Long,
)