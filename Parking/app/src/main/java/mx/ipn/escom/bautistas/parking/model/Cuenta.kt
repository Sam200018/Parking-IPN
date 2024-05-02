package mx.ipn.escom.bautistas.parking.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Cuenta (
    @SerializedName("id_cuenta")
    val idCuenta: Long,
    @SerializedName("id_persona")
    val idPersona: Long,
    @SerializedName("id_rol")
    val idRol: Long,
    @SerializedName("id_prog_academico")
    val idProgAcademico: Long,
    val activo: Boolean,
    val correo: String,
    val rol: Rol,
    @SerializedName("prog_academico")
    val progAcademico: ProgAcademico,
    val persona: Persona
)
