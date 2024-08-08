package mx.ipn.escom.bautistas.parking.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class CardInfoTokeResponse(
    @SerializedName("tarjeta_acceso")
    val accessCard: AccessCard,
    @SerializedName("movimiento")
    val movement: Int
)
