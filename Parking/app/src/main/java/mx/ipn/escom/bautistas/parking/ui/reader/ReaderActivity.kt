package mx.ipn.escom.bautistas.parking.ui.reader

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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ConnectWithoutContact
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dagger.hilt.android.AndroidEntryPoint
import mx.ipn.escom.bautistas.parking.services.Utils
import mx.ipn.escom.bautistas.parking.ui.reader.viewmodels.ScannerViewModel
import mx.ipn.escom.bautistas.parking.ui.reader.views.ScannedCardScreen
import mx.ipn.escom.bautistas.parking.ui.reader.views.ScannerScreen
import mx.ipn.escom.bautistas.parking.ui.theme.ParkingTheme

@AndroidEntryPoint
class ReaderActivity : ComponentActivity(), NfcAdapter.ReaderCallback {
    private val scannerViewModel: ScannerViewModel by viewModels()

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
            ParkingTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ScannerScreen()
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
            val cardTokenBytes = response.copyOfRange(2, response.size)
            val cardToken = String(cardTokenBytes)
            Log.i("HCE response", cardToken)



            scannerViewModel.scannerCardToken(cardToken)


            setContent {
                val scannerUiState by scannerViewModel.scannerUiState.collectAsStateWithLifecycle()
                ScannedCardScreen(
                    scannerViewModel = scannerViewModel,
                    scannerCardState = scannerUiState,
                ){
                    finish()
                }
            }

        } else {

            setContent {
                Greeting(name = "Error de lectura")
            }
        }

        isoDep.close()
    }
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