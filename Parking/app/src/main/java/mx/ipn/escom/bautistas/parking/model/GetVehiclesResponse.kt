package mx.ipn.escom.bautistas.parking.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class GetVehiclesResponse (
    @SerializedName("vehiculos")
    var vehicles: List<Vehicle>
)