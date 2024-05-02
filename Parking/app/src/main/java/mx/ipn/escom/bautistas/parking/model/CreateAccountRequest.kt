package mx.ipn.escom.bautistas.parking.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class CreateAccountRequest(
    @SerializedName("id_persona") val idPersona: Long,
    @SerializedName("id_rol") val idRol: Int,
    @SerializedName("id_prog_academico") val idProgAcademico: Int?,
    @SerializedName("correo") val email: String,
)
