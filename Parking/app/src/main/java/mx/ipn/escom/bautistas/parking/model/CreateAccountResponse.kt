package mx.ipn.escom.bautistas.parking.model

import android.accounts.Account
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class CreateAccountResponse(
    val message: String,
    @SerializedName("cuenta") val account: Account,
    @SerializedName("temp_password") val tempPassword: String
)
