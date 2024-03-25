package mx.ipn.escom.bautistas.parking.ui.main

import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.IsoDep
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.lifecycle.lifecycleScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ConnectWithoutContact
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import mx.ipn.escom.bautistas.parking.services.Utils
import mx.ipn.escom.bautistas.parking.ui.main.viewmodels.AuthViewModel
import mx.ipn.escom.bautistas.parking.ui.main.views.LoginScreen
import mx.ipn.escom.bautistas.parking.ui.theme.ParkingTheme

@ExperimentalMaterial3WindowSizeClassApi
@AndroidEntryPoint
class MainActivity : ComponentActivity(), NfcAdapter.ReaderCallback {
    private var mNfcAdapter: NfcAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this)?.let { it }

        super.onCreate(savedInstanceState)
        mNfcAdapter?.enableReaderMode(
            this,
            this,
            NfcAdapter.FLAG_READER_NFC_A or NfcAdapter.FLAG_READER_NFC_B,
            null
        )


        setContent {
            val windowSizeClass = calculateWindowSizeClass(this)
            val authViewModel: AuthViewModel by viewModels()


            ParkingTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = Routes.LoginScreen.route
                ) {
                    composable(Routes.SplashScreen.route) {

                    }
                    composable(Routes.LoginScreen.route) {
                        LoginScreen(
                            windowSizeClass = windowSizeClass,
                            authViewModel = authViewModel
                        )
                    }
                }

            }
        }

    }


    override fun onTagDiscovered(tag: Tag?) {
        val isoDep = IsoDep.get(tag)
        isoDep.connect()
        val response = isoDep.transceive(Utils.hexStringToByteArray("00A4040007A0000002471001"))

        val status = Utils.toHex(response)

        Log.i("Status", status.substring(0, 2))

        if (status.substring(0, 2) == "90") {
            val msgBytes = response.copyOfRange(2, response.size)

            Log.i("Card response", String(msgBytes))


            runOnUiThread {
                setContent {
                    Greeting(name = String(msgBytes))
                }
            }
        } else {

            setContent {
                Greeting(name = "Error de lectura")
            }
        }




        isoDep.close()
//        Greeting(name = String(msgBytes))
    }

    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {


        Scaffold {
            Box(
                modifier = modifier
                    .padding(it)
                    .fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                Column {
                    Text(text = name)
                    Icon(
                        Icons.Filled.ConnectWithoutContact,
                        contentDescription = "",
                        modifier.size(80.dp)
                    )
                }
            }
        }
    }

}
