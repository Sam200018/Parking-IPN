package mx.ipn.escom.bautistas.parking.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Visit(
    @SerializedName("id_visita")
    val visitId: Long,
    @SerializedName("id_persona")
    val personId: Int,
    @SerializedName("id_vehiculo")
    val vehicleId: Int,
    val persona: Persona,
    val vehiculo: Vehicle,
)
