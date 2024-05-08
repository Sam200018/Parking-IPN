package mx.ipn.escom.bautistas.parking.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Vehicle (
    @SerializedName("id_vehiculo")
    val idVehiculo: Long = 0 ,
    @SerializedName("tipo_vehiculo")
    val tipoVehiculo: Long,
    val placa: String,
    val marca: String,
    val modelo: String,
    val color: String,
    @SerializedName("ruta_documento")
    val rutaDocumento: String,
    val asignado: Boolean
)

