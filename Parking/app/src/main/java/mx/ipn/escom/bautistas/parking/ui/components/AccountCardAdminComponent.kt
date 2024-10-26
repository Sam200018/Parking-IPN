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
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Preview
import androidx.compose.material3.Card
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import mx.ipn.escom.bautistas.parking.model.AccountFromRes

@Composable
fun AccountAdminComponent(modifier: Modifier = Modifier, account: AccountFromRes) {
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
                        filename = account.persona.rutaFotografia
                    )
                    Spacer(modifier.width(16.dp))
                    Column(modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceAround) {
                        Text(
                            text = account.rol.nombreRol,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Nombre: ${account.persona.nombre + " " + account.persona.aPaterno + " " + account.persona.aMaterno}",
                            fontSize = 20.sp,
                        )
                        Text(
                            text = "Número de contacto: ${account.persona.numeroContacto}",
                            fontSize = 20.sp,
                            style = TextStyle(
                                textDecoration = TextDecoration.Underline,
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier.clickable {
                                val intent = Intent(Intent.ACTION_DIAL)
                                intent.data = Uri.parse("tel:${account.persona.numeroContacto}")
                                context.startActivity(intent)
                            }
                        )

                    }
                    Column(modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceAround) {
                        Text(
                            text = "No de vehiculos: ${account.noVehicles}",
                            fontSize = 20.sp,
                        )
                    }

                }
            }
            Box(
                modifier
                    .width(320.dp)
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
                        ActionButton(icon = Icons.Default.Preview, text = "Ver Documentos") {}
                        ActionButton(icon = Icons.Default.Edit, text = "Editar Datos") {}
                        SwitchButton(value = true) { }
                    }
                }
            }
        }
    }
}

@Composable
fun SwitchButton(modifier: Modifier = Modifier, value: Boolean, onAction: () -> Unit) {

    var checked by remember { mutableStateOf(value) }
    var text = if (checked) {
        "Activo"
    } else {
        "Inactivo"
    }

    IconButton(
        onClick = {
            onAction()
        },
        modifier
            .width(100.dp)
            .height(80.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Switch(
                checked = checked,
                onCheckedChange = {
                    onAction()
                }
            )
            Text(text, color = colorResource(R.color.guinda))
        }
    }
}