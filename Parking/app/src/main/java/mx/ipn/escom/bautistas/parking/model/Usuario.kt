package mx.ipn.escom.bautistas.parking.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Usuario (
    @SerializedName("id_usuario")
    val idUsuario: Long,
    val nombre: String,
    @SerializedName("a_paterno")
    val aPaterno: String,
    @SerializedName("a_materno")
    val aMaterno: String,
    @SerializedName("id_ipn")
    val idIpn: String,
    @SerializedName("ruta_identificacion")
    val rutaIdentificacion: String,
    @SerializedName("ruta_fotografia")
    val rutaFotografia: String,
    @SerializedName("numero_contacto")
    val numeroContacto: String
)
