package mx.ipn.escom.bautistas.parking.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import mx.ipn.escom.bautistas.parking.R


@Composable
fun DropDownComponent(
    modifier: Modifier = Modifier,
    items: List<String>,
    label: String,
    value: Int = 0,
    onChange: (Int) -> Unit,
) {


    var expanded by remember {
        mutableStateOf(false)
    }


    Column {
        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth(),
            value = items.get(value),
            label = {
                Text(text = label, color = colorResource(id = R.color.guinda))
            },
            onValueChange = {}, readOnly = true,
            trailingIcon = {
                IconButton(onClick = { expanded = true }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowDropDown, contentDescription = "",
                        tint = colorResource(
                            id = R.color.guinda
                        )
                    )

                }
            }
        )
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            items.forEachIndexed { index, label ->

                DropdownMenuItem(text = {
                    Text(text = label)
                }, onClick = {
                    expanded = false
                    onChange(index)
                })
            }
        }
    }

}

@Preview(showSystemUi = true)
@Composable
private fun DropDownPrev() {
    DropDownComponent(label = "Tipo de usuario", value = (6 - 1), items = typeUserOptions) {

    }
}