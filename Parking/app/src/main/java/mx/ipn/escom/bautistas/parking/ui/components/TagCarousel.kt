package mx.ipn.escom.bautistas.parking.ui.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


val PrerecordTags =
    listOf("Activos", "No activos", "Alumnos", "Empleados", "Empleados Temp", "Externos")

@Composable
fun TagCarousel(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier
            .height(40.dp)
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        PrerecordTags.forEach {
            ButtonComponent(label = it, fontSize = 20.sp, isEnable = false) {

            }
        }
    }
}