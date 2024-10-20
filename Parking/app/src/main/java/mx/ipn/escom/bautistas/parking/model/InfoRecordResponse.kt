package mx.ipn.escom.bautistas.parking.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class InfoRecordResponse(
    @SerializedName("tarjeta_acceso")
    val accessCard: AccessCard? = null,
    @SerializedName("movimiento")
    val movement: Int,
    @SerializedName("nota")
    val note: String,
    @SerializedName("visita")
    val visit: Visit? = null,
)
