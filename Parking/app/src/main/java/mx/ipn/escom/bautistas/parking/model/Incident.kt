package mx.ipn.escom.bautistas.parking.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Incident(
    @SerializedName("id_incidente")
    val incidentId: Long,
    @SerializedName("id_tarjeta_acceso")
    val tarjetaAccessoId: Long,
    @SerializedName("tarjeta_acceso")
    val accessCard: AccessCard,
    val titulo: String,
    val detalles: String,
    val cerrado: Boolean,
)
