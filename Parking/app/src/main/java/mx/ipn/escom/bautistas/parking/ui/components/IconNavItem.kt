package mx.ipn.escom.bautistas.parking.ui.components

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun IconNavItem(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    @StringRes title: Int,
    @ColorRes color: Int,
    size: Dp,
) {
    Column(
        modifier
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = modifier.size(size),
            imageVector = icon,
            contentDescription = stringResource(id = title),
            tint = colorResource(id = color)
        )
        Text(text = stringResource(id = title), color = colorResource(id = color))

    }
}
