package mx.ipn.escom.bautistas.parking.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.ipn.escom.bautistas.parking.R

@Composable
fun SelectionButtonComponent(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    label: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(50.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            modifier = modifier.size(120.dp),
            tint = colorResource(
                id = R.color.guinda
            )
        )
        ButtonComponent(fontSize = 24.sp, label = label) {
            onClick()
        }
    }
}
