package mx.ipn.escom.bautistas.parking.ui.main.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import mx.ipn.escom.bautistas.parking.R
import mx.ipn.escom.bautistas.parking.ui.components.AccountAdminComponent
import mx.ipn.escom.bautistas.parking.ui.components.FloatingActionButtonComponent
import mx.ipn.escom.bautistas.parking.ui.components.SearchListComponent
import mx.ipn.escom.bautistas.parking.ui.components.TopBarComponent
import mx.ipn.escom.bautistas.parking.ui.main.interactions.AdminUiState


@Composable
fun AccountsView(
    modifier: Modifier = Modifier,
    adminUiState: AdminUiState,
    navToCreateAccount: () -> Unit,
    logout: () -> Unit
) {
    Scaffold(
        topBar = {
            TopBarComponent(title = stringResource(id = R.string.accounts_label), actions = {
                IconButton(onClick = logout) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Logout,
                        contentDescription = "",
                        tint = Color.White
                    )
                }
            })
        },
        floatingActionButton = {
            FloatingActionButtonComponent {
                navToCreateAccount()
            }
        }
    ) {
        Box(modifier.padding(it)) {
            SearchListComponent(
                Modifier.fillMaxWidth(),
                searchLabel = stringResource(R.string.search_vehicle_label),
                value = "",
                emptyMsg = stringResource(R.string.element_not_found),
                items = adminUiState.accountsList,
                searchAction = {}
            ) {
                AccountAdminComponent(account = it)
            }
        }
    }
}