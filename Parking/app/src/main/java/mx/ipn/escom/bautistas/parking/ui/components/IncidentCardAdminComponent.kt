package mx.ipn.escom.bautistas.parking.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Details
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.ipn.escom.bautistas.parking.R
import mx.ipn.escom.bautistas.parking.model.Incident

@Composable
fun IncidentCardAdminComponent(modifier: Modifier = Modifier, incident: Incident) {
    val context = LocalContext.current
    Card(modifier.padding(10.dp), shape = RoundedCornerShape(10.dp)) {
        Row(
            modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = modifier
                    .height(143.dp)
                    .fillMaxWidth(.5f)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Spacer(modifier.width(16.dp))
                    BitmapImage(
                        modifier.size(100.dp),
                        filename = incident.accessCard.cuenta.persona.rutaFotografia
                    )
                    Spacer(modifier.width(16.dp))
                    Column(modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceAround) {
                        Text(
                            text = incident.titulo,
                            fontSize = 20.sp,
                        )
                        Text(
                            text = "Nombre: ${incident.accessCard.cuenta.persona.nombre + " " + incident.accessCard.cuenta.persona.aPaterno + " " + incident.accessCard.cuenta.persona.aMaterno}",
                            fontSize = 20.sp,
                        )
                        Text(
                            text = "Número de contacto: ${incident.accessCard.cuenta.persona.numeroContacto}",
                            fontSize = 20.sp,
                            style = TextStyle(
                                textDecoration = TextDecoration.Underline,
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier.clickable {
                                val intent = Intent(Intent.ACTION_CALL)
                                intent.data = Uri.parse("tel:${incident.accessCard.cuenta.persona.numeroContacto}")
                                context.startActivity(intent)
                            }
                        )
                    }
                    Column(modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceAround) {
                        Text(
                            text = "Placa ${incident.accessCard.vehiculo.placa}",
                            fontSize = 20.sp,
                        )
                    }

                }
            }
            Box(
                modifier
                    .width(290.dp)
                    .height(142.dp)
            ) {
                Column(
                    modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Acciones", color = colorResource(R.color.guinda), fontSize = 20.sp)
                    Row(
                        modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        ActionButton(icon = Icons.Default.Check, text = "Cerrar") {}
                        ActionButton(icon = Icons.Default.Details, text = "Mas detalles") {}
                    }
                }
            }
        }
    }
}