package mx.ipn.escom.bautistas.parking.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Rol (
    @SerializedName("id_rol")
    val idRol: Long,
    @SerializedName("nombre_rol")
    val nombreRol: String
)
