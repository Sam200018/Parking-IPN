package mx.ipn.escom.bautistas.parking.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class RecordsResponse(
    @SerializedName("transactions")
    val transactions: List<AccessControlTransaction>
)
