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
import mx.ipn.escom.bautistas.parking.ui.components.AccessCardAdminComponent
import mx.ipn.escom.bautistas.parking.ui.components.FloatingActionButtonComponent
import mx.ipn.escom.bautistas.parking.ui.components.SearchListComponent
import mx.ipn.escom.bautistas.parking.ui.components.TopBarComponent
import mx.ipn.escom.bautistas.parking.ui.main.interactions.AdminUiState

@Composable
fun AccessCardsView(
    modifier: Modifier = Modifier,
    navSelectUser: () -> Unit,
    logout: () -> Unit,
    adminUiState: AdminUiState
) {
    Scaffold(
        topBar = {
            TopBarComponent(title = stringResource(id = R.string.access_cards), actions = {
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
                navSelectUser()
            }
        }
    ) {
        Box(modifier.padding(it)) {
            SearchListComponent(
                modifier.fillMaxWidth(),
                searchLabel = stringResource(id = R.string.search_vehicle_label),
                value = "",
                emptyMsg = stringResource(id = R.string.element_not_found),
                buttonLabel = stringResource(id = R.string.gen_vehicle),
                items = adminUiState.accessCardsList,
                searchAction = {

                },
            ) {
                AccessCardAdminComponent(accessCard = it)
            }
        }
    }
}