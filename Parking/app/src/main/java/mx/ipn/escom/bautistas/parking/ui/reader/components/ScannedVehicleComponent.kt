package mx.ipn.escom.bautistas.parking.ui.reader.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Motorcycle
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.ipn.escom.bautistas.parking.R
import mx.ipn.escom.bautistas.parking.model.Vehicle
import mx.ipn.escom.bautistas.parking.ui.components.BalanceUI

@Composable
fun ScannedVehicleComponent(
    modifier: Modifier = Modifier,
    vehicle: Vehicle
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
            .height(160.dp).padding(10.dp)
    ) {
        Box(
            modifier = modifier
        ) {
            BalanceUI(
                content1 = {
                    Column(
                        verticalArrangement = Arrangement.SpaceAround,
                        modifier = modifier.fillMaxSize()
                    ) {
                        Text(
                            text = "Placa: ${vehicle.placa}",
                            fontSize = 20.sp,
                            style = TextStyle(fontWeight = FontWeight.Bold)
                        )
                        Text(text = "Modelo: ${vehicle.modelo}")
                        Text(text = "Marca: ${vehicle.marca}")
                        Text(text = "Color: ${vehicle.color}")
                    }
                }, content2 = {
                    if (vehicle.tipoVehiculo.equals(2.toLong())) {
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
            )
        }
    }
}