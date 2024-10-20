package mx.ipn.escom.bautistas.parking.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.ipn.escom.bautistas.parking.R
import mx.ipn.escom.bautistas.parking.model.Incident

@Composable
fun IncidentCard(
    modifier: Modifier = Modifier,
    incident: Incident,
    selectAction: (Long) -> Unit,
) {
    Card(
        onClick = { selectAction(incident.incidentId) },
        modifier
            .padding(10.dp),
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
                Icon(Icons.Filled.Warning, "")
            }
            Column(
                modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(10.dp), verticalArrangement = Arrangement.SpaceAround
            ) {

                Text(text = "Titulo: ${incident.titulo}")
                Text(text = "Nombre: ${incident.accessCard.cuenta.persona.nombre} ${incident.accessCard.cuenta.persona.aPaterno} ${incident.accessCard.cuenta.persona.aMaterno}")
                Text(text = "Placa: ${incident.accessCard.vehiculo.placa}")
            }
        }

    }
}
