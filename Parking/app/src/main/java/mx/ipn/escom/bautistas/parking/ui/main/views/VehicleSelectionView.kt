package mx.ipn.escom.bautistas.parking.ui.main.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import mx.ipn.escom.bautistas.parking.R
import mx.ipn.escom.bautistas.parking.ui.components.CompactFloatingActionButton
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
    navToNewVehicle: () -> Unit,
    backAction: () -> Unit
) {

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = manualRegistrationState.isError) {
        if (manualRegistrationState.isError && manualRegistrationState.message.isNotEmpty()) {
            snackbarHostState
                .showSnackbar(
                    message = manualRegistrationState.message,
                    duration = SnackbarDuration.Indefinite,
                    actionLabel = "Cerrar"
                )
        }
    }

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
                CompactFloatingActionButton(
                    icon = Icons.AutoMirrored.Filled.ArrowForward
                ) {
                    manualRegistrationViewModel.manualRegistrationRequest {
                        navToManualRegistrationResult()
                    }
                }
            }
        }
    ) {
        Box(modifier = modifier.padding(it), contentAlignment = Alignment.Center) {
            SearchListComponent(
                searchLabel = stringResource(id = R.string.search_vehicle_label),
                value = manualRegistrationViewModel.searchVehicle,
                emptyMsg = stringResource(id = R.string.element_not_found),
                buttonLabel = stringResource(id = R.string.gen_vehicle),
                items = manualRegistrationState.vehicleList,
                searchAction = {
                    manualRegistrationViewModel.onSearchVehicleInputChanged(it)
                },
                navAction = { navToNewVehicle() }
            ) {
                VehicleCard(
                    vehicle = it,
                    selectedItem = manualRegistrationViewModel.vehicleIdSelected
                ) {
                    manualRegistrationViewModel.onVehicleSelected(it)
                }
            }
        }
//        if (manualRegistrationState.isLoading) {
//            LoadingDialogComponent()
//        }
    }
}