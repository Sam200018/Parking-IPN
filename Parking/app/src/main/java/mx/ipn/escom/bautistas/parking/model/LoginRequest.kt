package mx.ipn.escom.bautistas.parking.model

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("correo") val email:String,
    @SerializedName("password") val password:String,

)
