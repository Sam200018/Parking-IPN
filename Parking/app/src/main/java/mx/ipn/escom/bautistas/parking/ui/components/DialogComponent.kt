package mx.ipn.escom.bautistas.parking.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.ipn.escom.bautistas.parking.R

@Composable
fun DialogComponent(
    modifier: Modifier = Modifier,
    title: String? = null,
    message: String,
    icon: ImageVector,
    onDismissRequest: () -> Unit,
) {

    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {

            TextButton(onClick = onDismissRequest) {
                Text(text = stringResource(id = R.string.close_button_label))
            }
        },
        title = {
            if (title != null)
                Text(title, color = colorResource(id = R.color.guinda))
        },
        text = {
            if (title != null){

                Text(message)
            }else{
                Text(message, color = colorResource(id = R.color.guinda), fontSize = 24.sp)
            }
        },
        icon = {
            Icon(icon, contentDescription = "", tint = colorResource(id = R.color.guinda),modifier= modifier.size(60.dp))
        }
    )
}

@Composable
fun LoadingDialogComponent(modifier: Modifier = Modifier) {
    AlertDialog(onDismissRequest = { /*TODO*/ }, confirmButton = { /*TODO*/ }, text = {
        Box(modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {

        CircularProgressIndicator()
        }
    })
}

@Preview(showBackground = true)
@Composable
private fun DialogPrev() {
//    DialogComponent(title = null, message = "hola", icon = Icons.Filled.CheckCircle) {
//
//    }
    LoadingDialogComponent()
}