package mx.ipn.escom.bautistas.parking.model

import kotlinx.serialization.Serializable

@Serializable
data class CheckAccountResponse(
    val message: String,
    val verified: Boolean
)
