package mx.ipn.escom.bautistas.parking

import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import mx.ipn.escom.bautistas.parking.ui.theme.ParkingTheme

class NDEFMessage : ComponentActivity() {
    private lateinit var nfcAdapter: NfcAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)

        val message = NdefMessage(arrayOf(NdefRecord.createTextRecord("es", "Hola"),
            ))

        val responseApdu = message.toByteArray()




        setContent {
            ParkingTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MessageScreen("Android")
                }
            }
        }
    }
}

@Composable
fun MessageScreen(name: String, modifier: Modifier = Modifier) {
    Scaffold {
        Box(modifier = modifier.padding(it), contentAlignment = Alignment.Center) {
            Text(text = "Send message")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    ParkingTheme {
        MessageScreen("Android")
    }
}