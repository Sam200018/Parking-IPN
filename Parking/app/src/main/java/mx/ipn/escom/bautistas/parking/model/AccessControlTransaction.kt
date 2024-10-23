package mx.ipn.escom.bautistas.parking.model

import com.google.gson.annotations.SerializedName
import java.util.Date

data class AccessControlTransaction(
    @SerializedName("id_entrada")
    val idEntrada: Long?=null,
    @SerializedName("id_salida")
    val idSalida: Long?=null,
    val registro: Registro,
    val cuenta: Cuenta,
    val check: Date
)
