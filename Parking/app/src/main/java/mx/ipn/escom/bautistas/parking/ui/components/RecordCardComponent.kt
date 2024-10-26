package mx.ipn.escom.bautistas.parking.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Input
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.filled.Handshake
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import mx.ipn.escom.bautistas.parking.R
import mx.ipn.escom.bautistas.parking.model.AccessControlTransaction

@Composable
fun RecordCardComponent(modifier: Modifier = Modifier, transaction: AccessControlTransaction) {
    val name = transaction.registro.tarjetaAcceso?.cuenta?.persona?.nombre
        ?: transaction.registro.visita?.persona?.nombre ?: "Sin dato"

    val lastNameP = transaction.registro.tarjetaAcceso?.cuenta?.persona?.aPaterno
        ?: transaction.registro.visita?.persona?.aPaterno ?: "Sin dato"

    val lastNameM = transaction.registro.tarjetaAcceso?.cuenta?.persona?.aMaterno
        ?: transaction.registro.visita?.persona?.aMaterno ?: "Sin dato"

    val ipnID = transaction.registro.tarjetaAcceso?.cuenta?.persona?.idIpn
    val plate = transaction.registro.tarjetaAcceso?.vehiculo?.placa ?: "Sin dato"


    Card(modifier = modifier.height(160.dp), onClick = {

    }) {
        Box(modifier.padding(10.dp)) {

            Row(
                modifier = modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(modifier.size(100.dp, 100.dp), contentAlignment = Alignment.Center) {
                    if (transaction.idEntrada != null) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.Input,
                            contentDescription = "",
                            modifier = modifier.size(80.dp),
                            tint = colorResource(id = R.color.guinda)
                        )
                    } else if (transaction.idSalida != null) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.Logout,
                            contentDescription = "",
                            modifier = modifier.size(80.dp),
                            tint = colorResource(id = R.color.guinda)
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Filled.QuestionMark,
                            contentDescription = "",
                            modifier = modifier.size(80.dp),
                            tint = colorResource(id = R.color.guinda)
                        )
                    }

                }
                Column(
                    modifier
                        .fillMaxWidth(0.7f)
                        .fillMaxHeight()
                        .padding(10.dp), verticalArrangement = Arrangement.SpaceAround
                ) {


                    Text(text = "Nombre: $name + $lastNameP + $lastNameM")
                    if (ipnID != null)
                        Text("IPN ID: $ipnID ")
                    Text("Placa: $plate")

                }
                Column(modifier.fillMaxHeight(), horizontalAlignment = Alignment.End) {
                    Text("${transaction.check}")
                    Box(modifier.size(100.dp, 100.dp), contentAlignment = Alignment.Center) {
                        IconButton(onClick = {}) {
                            Icon(
                                imageVector = Icons.Filled.Handshake,
                                contentDescription = "",
                                modifier = modifier.size(80.dp),
                                tint = colorResource(id = R.color.guinda)
                            )
                        }
                    }
                    if (transaction.registro.visita != null) {
                        Icon(Icons.Filled.Timer, "")
                    }
                }
            }
        }
    }
}