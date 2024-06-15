package mx.ipn.escom.bautistas.parking.ui.reader.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.sp
import mx.ipn.escom.bautistas.parking.R

@Composable
fun RadioButtonComponent(
    modifier: Modifier = Modifier,
    label: String = "",
    selected: Boolean,
    onCheckChange: () -> Unit,
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.clickable {
            onCheckChange()
        }
    ) {
        RadioButton(selected = selected, onClick = onCheckChange, colors = RadioButtonColors(
            selectedColor = colorResource(id = R.color.guinda),
            unselectedColor = Color.Gray,
            disabledSelectedColor = Color.Transparent,
            disabledUnselectedColor = Color.Transparent
        ))
        Text(
            label,
            fontSize = 15.sp
        )
    }

}