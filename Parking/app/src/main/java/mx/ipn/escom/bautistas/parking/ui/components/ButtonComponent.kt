package mx.ipn.escom.bautistas.parking.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import mx.ipn.escom.bautistas.parking.R

@Composable
fun ButtonComponent(
    modifier: Modifier = Modifier,
    label: String = "",
    isEnable: Boolean = true,
    isCancelBtn: Boolean = false,
    fontSize: TextUnit,
    action: () -> Unit,
) {
    OutlinedButton(
        onClick = action,
        modifier.fillMaxWidth(),
        enabled = isEnable,
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = if (isCancelBtn) colorResource(id = R.color.cancel) else colorResource(id = R.color.guinda),
            contentColor = if (isCancelBtn) colorResource(R.color.black) else colorResource(id = R.color.white),
            disabledContainerColor = colorResource(id = R.color.guinda_disbale),
        )
    ) {
        Text(text = label, fontSize = fontSize)
    }
}

@Preview(showBackground = true)
@Composable
fun ButtonCompPrev() {
    ButtonComponent(label = "Hola", fontSize = 24.sp) {}
}