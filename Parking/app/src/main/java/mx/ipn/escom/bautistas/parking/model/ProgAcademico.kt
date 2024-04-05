package mx.ipn.escom.bautistas.parking.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class ProgAcademico (
    @SerializedName("id_prog_academico")
    val idProgAcademico: Long,
    @SerializedName("nombre_prog_academico")
    val nombreProgAcademico: String
)
