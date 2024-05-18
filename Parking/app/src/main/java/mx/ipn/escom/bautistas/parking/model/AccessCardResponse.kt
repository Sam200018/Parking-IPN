package mx.ipn.escom.bautistas.parking.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class AccessCardResponse(
    @SerializedName("tarjetas_acceso")
    val accessCards: List<AccessCard>
)
