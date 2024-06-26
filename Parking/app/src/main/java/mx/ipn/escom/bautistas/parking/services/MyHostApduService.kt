package mx.ipn.escom.bautistas.parking.services

import android.content.Context
import android.nfc.cardemulation.HostApduService
import android.os.Bundle
import android.util.Log

class MyHostApduService : HostApduService() {
    companion object {
        val TAG = "Host Card Emulator"
        val STATUS_SUCCESS = "9000"
        val STATUS_FAILED = "6F00"
        val CLA_NOT_SUPPORTED = "6E00"
        val INS_NOT_SUPPORTED = "6D00"
        val AID = "A0000002471001"
        val SELECT_INS = "A4"
        val DEFAULT_CLA = "00"
        val MIN_APDU_LENGTH = 12
    }

    override fun onCreate() {
        Log.d("HCE", "hola")
        super.onCreate()
    }

    override fun processCommandApdu(commandApdu: ByteArray?, extras: Bundle?): ByteArray {

        val hash = getHash(this)

        if (commandApdu == null) {
            return Utils.hexStringToByteArray(STATUS_FAILED)
        }

        val hexCommandApdu = Utils.toHex(commandApdu)
        if (hexCommandApdu.length < MIN_APDU_LENGTH) {
            return Utils.hexStringToByteArray(STATUS_FAILED)
        }

        if (hexCommandApdu.substring(0, 2) != DEFAULT_CLA) {
            return Utils.hexStringToByteArray(CLA_NOT_SUPPORTED)
        }

        if (hexCommandApdu.substring(2, 4) != SELECT_INS) {
            return Utils.hexStringToByteArray(INS_NOT_SUPPORTED)
        }

        if (hexCommandApdu.substring(10, 24) == AID && hash != null) {
            Log.i(
                "response",
                hash
            )
            return Utils.hexStringToByteArray(STATUS_SUCCESS) + hash.toByteArray()
        } else {
            return Utils.hexStringToByteArray(STATUS_FAILED)
        }
    }

    override fun onDeactivated(p0: Int) {
        Log.d(TAG, "HCE desactivado, razón: $p0")
    }

}

fun getHash(context: Context): String? {
    val sharedPreferences = context.getSharedPreferences("HCEPrefs", Context.MODE_PRIVATE)
    return sharedPreferences.getString("hash", null)
}