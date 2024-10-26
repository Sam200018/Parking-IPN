package mx.ipn.escom.bautistas.parking.ui.components

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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CarRental
import androidx.compose.material.icons.filled.DisabledByDefault
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.ipn.escom.bautistas.parking.R
import mx.ipn.escom.bautistas.parking.model.AccessCard

@Composable
fun AccessCardAdminComponent(modifier: Modifier = Modifier, accessCard: AccessCard) {
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
                        filename = accessCard.cuenta.persona.rutaFotografia
                    )
                    Spacer(modifier.width(16.dp))
                    Column(modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceAround) {
                        Text(
                            text = "Nombre: ${accessCard.cuenta.persona.nombre + " " + accessCard.cuenta.persona.aPaterno + " " + accessCard.cuenta.persona.aMaterno}",
                            fontSize = 20.sp,
                        )
                        Text(
                            text = "Contacto: ${accessCard.cuenta.persona.numeroContacto}",
                            fontSize = 20.sp,
                        )
                        Text(
                            text = "Tipo de usuario: ${accessCard.cuenta.rol.nombreRol}",
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
                        ActionButton(icon = Icons.Default.CarRental, text = "Vehiculo") {}
                        ActionButton(icon = Icons.Default.Add, text = "Mas") {}
                        ActionButton(icon = Icons.Default.DisabledByDefault, text = "Eliminar") {}
                    }
                }
            }
        }
    }
}

@Composable
fun ActionButton(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    text: String,
    onAction: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .height(100.dp)
            .width(100.dp)
    ) {
        IconButton(onClick = {
            onAction()
        }) {
            Icon(
                icon, "", modifier.size(50.dp),
                tint = colorResource(R.color.guinda)
            )
        }
        Text(text, color = colorResource(R.color.guinda), textAlign = TextAlign.Center)
    }
}
