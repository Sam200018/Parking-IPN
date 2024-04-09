package mx.ipn.escom.bautistas.parking.ui.main.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.SupervisorAccount
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import mx.ipn.escom.bautistas.parking.R
import mx.ipn.escom.bautistas.parking.ui.components.BalanceUI
import mx.ipn.escom.bautistas.parking.ui.components.SelectionButtonComponent
import mx.ipn.escom.bautistas.parking.ui.components.TopBarComponent


@Composable
fun UserSelectScreen(
    modifier: Modifier = Modifier,
    backAction: () -> Unit,
    navUserAvailable: () -> Unit,
    navNewUser: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopBarComponent(title = stringResource(id = R.string.pre_record_label)) {
                backAction()
            }
        }
    ) {
        Box(modifier = modifier.padding(it)) {
            BalanceUI(content1 = {
                SelectionButtonComponent(
                    icon = Icons.Filled.SupervisorAccount, label = stringResource(
                        id = R.string.users_available_label
                    )
                ) {
                    navUserAvailable()
                }

            }, content2 = {
                SelectionButtonComponent(
                    icon = Icons.Filled.PersonAdd, label = stringResource(
                        id = R.string.new_user_label
                    )
                ) {
                    navNewUser()
                }
            })
        }
    }
}

@Preview(device = Devices.TABLET)
@Composable
private fun UserSelectionPrev() {
    UserSelectScreen(backAction = { /*TODO*/ }, navUserAvailable = { /*TODO*/ }) {

    }
}

