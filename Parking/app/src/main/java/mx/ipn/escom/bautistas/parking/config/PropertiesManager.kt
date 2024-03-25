package mx.ipn.escom.bautistas.parking.config

import android.content.Context
import java.util.Properties

class PropertiesManager(context: Context) {
    private val properties = Properties()

    init {
        context.assets.open("app.properties").use {
            properties.load(it)
        }
    }

    fun getString(key: String): String? = properties.getProperty(key)
}