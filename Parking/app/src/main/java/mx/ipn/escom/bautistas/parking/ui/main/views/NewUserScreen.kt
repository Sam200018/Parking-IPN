package mx.ipn.escom.bautistas.parking.ui.main.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.ipn.escom.bautistas.parking.R
import mx.ipn.escom.bautistas.parking.ui.components.BalanceUI
import mx.ipn.escom.bautistas.parking.ui.components.DropDownComponent
import mx.ipn.escom.bautistas.parking.ui.components.TextFieldComponent
import mx.ipn.escom.bautistas.parking.ui.components.typeUserOptions
import mx.ipn.escom.bautistas.parking.ui.main.Routes

@Composable
fun NewUserScreen(
    modifier: Modifier = Modifier
) {
    Scaffold {
        Box(modifier = modifier.padding(it)) {
            BalanceUI(
                content1 = {
                    Column(modifier.fillMaxSize()) {
                        DropDownComponent(
                            items = typeUserOptions,
                            label = stringResource(id = R.string.type_user_label)
                        ) {

                        }
                        Row(modifier.fillMaxWidth()) {
                            TextFieldComponent {

                            }
                            TextFieldComponent {

                            }
                        }
                        TextFieldComponent(modifier.fillMaxWidth()) {

                        }

                        TextFieldComponent(modifier.fillMaxWidth()) {

                        }
                        TextFieldComponent(modifier.fillMaxWidth()) {

                        }
                        TextFieldComponent(modifier.fillMaxWidth()) {

                        }
                        TextFieldComponent(modifier.fillMaxWidth()) {

                        }
                    }
                }, content2 = {
                    Text(text = "Hola")
                }
            )
        }
    }
}

@Preview(showSystemUi = true, device = Devices.TABLET)
@Composable
private fun NewUserPrev() {
    NewUserScreen()
}