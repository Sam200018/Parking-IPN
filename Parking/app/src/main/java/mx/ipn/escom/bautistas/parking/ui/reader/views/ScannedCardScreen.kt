package mx.ipn.escom.bautistas.parking.ui.reader.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.ipn.escom.bautistas.parking.R
import mx.ipn.escom.bautistas.parking.ui.components.LoadingDialogComponent
import mx.ipn.escom.bautistas.parking.ui.components.TopBarComponent
import mx.ipn.escom.bautistas.parking.ui.reader.components.ScannedPersonaComponent
import mx.ipn.escom.bautistas.parking.ui.reader.components.ScannedVehicleComponent
import mx.ipn.escom.bautistas.parking.ui.reader.interactions.ScannerCardState
import mx.ipn.escom.bautistas.parking.ui.reader.viewmodels.ScannerViewModel

@Composable
fun ScannedCardScreen(
    modifier: Modifier = Modifier,
    scannerViewModel: ScannerViewModel,
    scannerCardState: ScannerCardState
) {


    Scaffold(
        topBar = {
            TopBarComponent(title = stringResource(id = R.string.data_label))
        }
    ) {

        Box(modifier.padding(it)) {
            Column(
                modifier
                    .fillMaxSize()
                    .padding(10.dp)
            ) {
                Box(
                    modifier
                        .height(400.dp)
                        .fillMaxWidth()
                ) {
                    Column(modifier.fillMaxSize()) {
                        Text(
                            text = "Datos personales",
                            color = colorResource(id = R.color.guinda),
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold
                        )
                        if (scannerCardState.accessCard != null) {
                            ScannedPersonaComponent(persona = scannerCardState.accessCard.cuenta.persona, cuenta = scannerCardState.accessCard.cuenta)

                        }
                    }
                }
                Box(
                    modifier
                        .height(293.dp)
                        .fillMaxWidth()
                ) {
                    Column(modifier.fillMaxSize()) {
                        Text(
                            text = "Datos del vehiculo",
                            color = colorResource(id = R.color.guinda),
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold
                        )
                        if (scannerCardState.accessCard != null) {
                            ScannedVehicleComponent(vehicle = scannerCardState.accessCard.vehiculo)
                        }
                    }
                }
            }

            if(scannerCardState.isLoading){
                LoadingDialogComponent()
            }

        }
    }
}
