package mx.ipn.escom.bautistas.parking.ui.main.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import mx.ipn.escom.bautistas.parking.R
import mx.ipn.escom.bautistas.parking.ui.components.SearchListComponent
import mx.ipn.escom.bautistas.parking.ui.components.TopBarComponent
import mx.ipn.escom.bautistas.parking.ui.components.VehicleCard
import mx.ipn.escom.bautistas.parking.ui.main.interactions.ManualRegistrationState
import mx.ipn.escom.bautistas.parking.ui.main.viewmodels.ManualRegistrationViewModel

@Composable
fun VehicleSelectionView(
    modifier: Modifier = Modifier,
    manualRegistrationViewModel: ManualRegistrationViewModel,
    manualRegistrationState: ManualRegistrationState,
    navToManualRegistrationResult: () -> Unit,
    backAction: () -> Unit
) {
    Scaffold(
        topBar = {
            TopBarComponent(
                title = stringResource(id = R.string.manual_registration_title),
                backAction = {
                    backAction()
                }
            )
        },
        floatingActionButton = {
            if (manualRegistrationViewModel.vehicleIdSelected != 0.toLong()) {
                manualRegistrationViewModel.saveManualRegistration() {
                    navToManualRegistrationResult()
                }
            }
        }
    ) {
        Box(modifier = modifier.padding(it), contentAlignment = Alignment.Center) {
            SearchListComponent(
                searchLabel = stringResource(id = R.string.search_vehicle_label),
                value = "",
                items = manualRegistrationState.vehicleList,
                searchAction = {},
                navAction = { /*TODO*/ }
            ) {
                VehicleCard(
                    vehicle = it,
                    selectedItem = manualRegistrationViewModel.vehicleIdSelected
                ) {

                }
            }
        }
    }
}