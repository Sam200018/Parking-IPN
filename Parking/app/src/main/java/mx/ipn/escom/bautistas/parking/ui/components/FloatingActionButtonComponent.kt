package mx.ipn.escom.bautistas.parking.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import mx.ipn.escom.bautistas.parking.R

@Composable
fun FloatingActionButtonComponent(
    modifier: Modifier = Modifier,
    icon: ImageVector = Icons.Outlined.Add,
    onClick: () -> Unit
) {
    LargeFloatingActionButton(
        onClick = onClick, shape = CircleShape, containerColor = colorResource(
            id = R.color.guinda
        )
    ) {
        Icon(
            imageVector = icon, contentDescription = "",
            tint = colorResource(
                id = R.color.white
            ),
            modifier = modifier.size(40.dp)
        )
    }
}