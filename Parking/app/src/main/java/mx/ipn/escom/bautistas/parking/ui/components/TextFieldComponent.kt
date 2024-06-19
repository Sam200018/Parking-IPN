package mx.ipn.escom.bautistas.parking.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.ipn.escom.bautistas.parking.R
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun TextFieldComponent(
    modifier: Modifier = Modifier,
    singleLine: Boolean = true,
    icon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    value: String = "",
    label: String = "",
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    readOnly: Boolean = false,
    isError: Boolean = false,
    errorMessage: String = "",
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onChanged: (String) -> Unit
) {

    Column {
        OutlinedTextField(
            leadingIcon = icon,
            singleLine = singleLine,
            value = value,
            trailingIcon = trailingIcon,
            onValueChange = onChanged,
            label = {
                Text(text = label, color = colorResource(id = R.color.guinda))
            },
            visualTransformation = visualTransformation ,
            readOnly = readOnly,
            modifier = modifier,
            keyboardOptions = keyboardOptions,
            isError = isError,
            supportingText = {
                if (isError) {
                    Text(text = errorMessage)
                }
            }
        )
        Spacer(modifier = modifier.height(5.dp))
    }
}

@Preview(showSystemUi = true)
@Composable
private fun TextFieldPrev() {
    TextFieldComponent() {}
}