package mx.ipn.escom.bautistas.parking.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.ipn.escom.bautistas.parking.R
import mx.ipn.escom.bautistas.parking.model.Persona

@Composable
fun PersonaCard(
    modifier: Modifier = Modifier,
    persona: Persona,
    selectedItem: Long = 0,
    selectAction: (Long) -> Unit
) {
    Card(
        modifier
            .padding(10.dp)
            .clickable {
                selectAction(persona.idPersona)
            },
        border = if (selectedItem == persona.idPersona) {
            BorderStroke(5.dp, colorResource(id = R.color.guinda))

        } else {
            null
        },
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.guinda_disbale + 9)
        )
    ) {
        Row(
            modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Box(modifier.size(100.dp, 100.dp)) {
                BitmapImage(filename = persona.rutaFotografia)
            }
            Column(
                modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(10.dp), verticalArrangement = Arrangement.SpaceAround
            ) {
                Text(text = "Nombre: ${persona.nombre} ${persona.aPaterno} ${persona.aMaterno}")
                Text(text = "IPN ID: ${persona.idIpn}")
                Text(text = "Teléfono: ${persona.numeroContacto}")
            }
        }

    }

}

@Preview(showSystemUi = true)
@Composable
private fun PersonaCardPrev() {
    PersonaCard(
        persona = Persona(
            nombre = "Sam",
            aPaterno = "BAu",
            aMaterno = "bas",
            rutaIdentificacion = "https://avatars.githubusercontent.com/u/49082936?v=4",
            rutaFotografia = "",
            numeroContacto = "555555555"
        )
    ) {}
}