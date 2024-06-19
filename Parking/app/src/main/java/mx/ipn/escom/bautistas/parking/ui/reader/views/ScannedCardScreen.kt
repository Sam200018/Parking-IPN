package mx.ipn.escom.bautistas.parking.ui.reader.views

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.SecurityUpdateWarning
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.ipn.escom.bautistas.parking.R
import mx.ipn.escom.bautistas.parking.ui.components.ButtonComponent
import mx.ipn.escom.bautistas.parking.ui.components.DialogComponent
import mx.ipn.escom.bautistas.parking.ui.components.LoadingDialogComponent
import mx.ipn.escom.bautistas.parking.ui.components.TopBarComponent
import mx.ipn.escom.bautistas.parking.ui.reader.components.RadioButtonComponent
import mx.ipn.escom.bautistas.parking.ui.reader.components.ScannedPersonaComponent
import mx.ipn.escom.bautistas.parking.ui.reader.components.ScannedVehicleComponent
import mx.ipn.escom.bautistas.parking.ui.reader.interactions.InfoStatus
import mx.ipn.escom.bautistas.parking.ui.reader.interactions.Movement
import mx.ipn.escom.bautistas.parking.ui.reader.interactions.ScannerCardState
import mx.ipn.escom.bautistas.parking.ui.reader.viewmodels.ScannerViewModel

@Composable
fun ScannedCardScreen(
    modifier: Modifier = Modifier,
    scannerViewModel: ScannerViewModel,
    scannerCardState: ScannerCardState,
    backAction: () -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val movementLabel = when (scannerCardState.movement) {
        Movement.CheckIn -> "Entrada"
        Movement.CheckOut -> "Salida"
        else -> "Desconocido"
    }

    LaunchedEffect(key1 = scannerCardState.isError) {
        if (scannerCardState.isError && scannerCardState.message.isNotEmpty()) {
            snackbarHostState
                .showSnackbar(
                    message = scannerCardState.message,
                    duration = SnackbarDuration.Indefinite,
                    actionLabel = "Cerrar"
                )
        }
    }

    Scaffold(
        topBar = {
            TopBarComponent(title = stringResource(id = R.string.data_label)) {
                backAction()
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState){data->
                Snackbar(
                    action = {
                        TextButton(
                            onClick = { backAction() },
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = Color.Cyan,
                            )
                        ) {
                            Text(data.visuals.actionLabel ?: "")
                        }
                    }
                ) {
                    Text(data.visuals.message)
                }

            }
        }
    ) {
        Box(modifier.padding(it)) {
            Column(
                modifier
                    .fillMaxSize()
                    .padding(10.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Tipo de Movimiento: ",
                        fontWeight = FontWeight.Bold,
                        color = colorResource(
                            id = R.color.movement
                        ),
                        fontSize = 20.sp
                    )
                    Text(
                        text = movementLabel,
                        color = colorResource(id = R.color.movement),
                        fontSize = 20.sp
                    )
                }
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
                            ScannedPersonaComponent(
                                persona = scannerCardState.accessCard.cuenta.persona,
                                cuenta = scannerCardState.accessCard.cuenta
                            )

                        }
                    }
                }
                Box(
                    modifier
                        .height(200.dp)
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
                Row(
                    modifier = modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.SecurityUpdateWarning,
                        contentDescription = "",
                        modifier.size(50.dp),
                    )
                    Text(
                        text = "Verifica la información mostrada con el conductor y el vehiculo",
                        fontSize = 15.sp
                    )
                }
                RadioButtonComponent(
                    label = "La informacion coincide",
                    selected = scannerCardState.infoStatus == InfoStatus.Verified
                ) {
                    scannerViewModel.allInfoOkaySelectChange()
                }
                RadioButtonComponent(
                    label = "La informacion no coincide ",
                    selected = scannerCardState.infoStatus == InfoStatus.NotVerified
                ) {
                    scannerViewModel.notAllInfoOkaySelectChange()
                }
                ButtonComponent(
                    fontSize = 20.sp,
                    label = "Realizar registro",
                    isEnable = (scannerCardState.infoStatus != InfoStatus.Unverified)
                ) {
                    scannerViewModel.onRegisterMov()
                }

            }

            if (scannerCardState.isLoading) {
                LoadingDialogComponent()
            }

            if (scannerCardState.isSuccess) {
                DialogComponent(
                    message = scannerCardState.message,
                    icon = Icons.Filled.CheckCircle
                ) {
                    backAction()
                }
            }
        }
    }
}
