package mx.ipn.escom.bautistas.parking.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class GetAccountsResponse(
    @SerializedName("cuentas")
    val accounts: List<AccountFromRes>
)

@Serializable
data class AccountFromRes(
    @SerializedName("id_cuenta")
    val idCuenta: Long,
    val activo: Boolean,
    val correo: String,
    @SerializedName("no_vehiculos")
    val noVehicles: Int,
    val rol: Rol,
    @SerializedName("prog_academico")
    val progAcademico: ProgAcademico,
    val persona: Persona
)
