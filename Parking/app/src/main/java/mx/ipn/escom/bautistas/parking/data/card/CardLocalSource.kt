package mx.ipn.escom.bautistas.parking.data.card

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class CardLocalSource @Inject constructor(private val context: Context) {
    companion object {
        private const val PREFS_NAME = "HCEPrefs"
        private const val PREF_KEY_HASH = "hash"
    }

    private val sharedPreferences: SharedPreferences
        get() = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun saveHash(hash: String) {
        with(sharedPreferences.edit()) {
            putString(PREF_KEY_HASH, hash)
            apply()
        }
    }

    fun getHash(): String? {
        return sharedPreferences.getString(PREF_KEY_HASH, null)
    }
}