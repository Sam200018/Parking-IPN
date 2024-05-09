package mx.ipn.escom.bautistas.parking.ui.components

import android.graphics.drawable.Icon
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Motorcycle
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
import mx.ipn.escom.bautistas.parking.model.Vehicle

@Composable
fun VehicleCard(
    modifier: Modifier = Modifier,
    vehicle: Vehicle,
    selectedItem: Long = 0,
    selectAction: (Long) -> Unit
) {

    Card(
        onClick = { selectAction(vehicle.idVehiculo) },
        modifier
            .padding(10.dp),
        border = if (selectedItem == vehicle.idVehiculo) {
            BorderStroke(5.dp, colorResource(id = R.color.guinda))

        } else {
            null
        },
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.guinda_disbale + 9)
        ),

        ) {
        Row(
            modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Box(modifier.size(100.dp, 100.dp)) {
                if (vehicle.tipoVehiculo.equals(1.toLong())) {
                    Icon(
                        Icons.Filled.DirectionsCar,
                        contentDescription = "",
                        modifier = modifier.fillMaxSize(),
                        tint = colorResource(id = R.color.guinda)
                    )

                } else {
                    Icon(
                        Icons.Filled.Motorcycle,
                        contentDescription = "",
                        modifier = modifier.fillMaxSize(),
                        tint = colorResource(id = R.color.guinda)
                    )
                }

            }
            Column(
                modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(10.dp), verticalArrangement = Arrangement.SpaceAround
            ) {
                Text(text = "Placa: ${vehicle.placa} ")
                Text(text = "Marca: ${vehicle.marca}")
                Text(text = "Modelo: ${vehicle.modelo}")
            }
        }

    }
}

@Preview(showSystemUi = true)
@Composable
private fun VehicleCardPrev() {
    VehicleCard(
        vehicle = Vehicle(
            tipoVehiculo = 1.toLong(),
            placa = "",
            marca = "",
            modelo = "",
            color = "",
            rutaDocumento = "",
            asignado = false
        )
    ) {

    }
}