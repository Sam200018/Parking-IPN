package mx.ipn.escom.bautistas.parking.ui.main.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import mx.ipn.escom.bautistas.parking.R
import mx.ipn.escom.bautistas.parking.ui.components.CompactFloatingActionButton
import mx.ipn.escom.bautistas.parking.ui.components.PersonaCard
import mx.ipn.escom.bautistas.parking.ui.components.SearchListComponent
import mx.ipn.escom.bautistas.parking.ui.components.TopBarComponent
import mx.ipn.escom.bautistas.parking.ui.main.Routes
import mx.ipn.escom.bautistas.parking.ui.main.interactions.ManualRegistrationState
import mx.ipn.escom.bautistas.parking.ui.main.viewmodels.ManualRegistrationViewModel

@Composable
fun ManualRegistrationScreen(
    modifier: Modifier = Modifier,
    navToNewUser: () -> Unit,
    navToNewVehicle: () -> Unit,
    backAction: () -> Unit
) {
    val navHostManualRegistration = rememberNavController()

//    View model con user y vehicle para generar un rapida operacion de registro
    val manualRegistrationViewModel: ManualRegistrationViewModel = hiltViewModel()
    val manualRegistrationState by manualRegistrationViewModel.manualRegistrationState.collectAsStateWithLifecycle()


    NavHost(
        navController = navHostManualRegistration,
        startDestination = Routes.ManualRegistrationSelect.route
    ) {
        composable(Routes.ManualRegistrationSelect.route) {
            ManualRegistrationSelect(
                manualRegistrationViewModel = manualRegistrationViewModel,
                manualRegistrationState = manualRegistrationState,
                navToNewUser = navToNewUser,
                navToVehicleSelection = {
                    navHostManualRegistration.navigate(Routes.ManualRegistrationVehicle.route)
                }
            ) {
                backAction()
            }
        }
        composable(Routes.ManualRegistrationVehicle.route) {
            VehicleSelectionView(
                manualRegistrationState = manualRegistrationState,
                manualRegistrationViewModel = manualRegistrationViewModel,
                navToManualRegistrationResult = {
                    navHostManualRegistration.navigate(Routes.ManualRegistrationResult.route)
                },
                navToNewVehicle = navToNewVehicle
            ) {
                navHostManualRegistration.popBackStack()
            }
        }
        composable(Routes.ManualRegistrationResult.route) {
            ManualRegistrationResult(
                closeAction = backAction,
                manualRegistrationState = manualRegistrationState,
                manualRegistrationViewModel = manualRegistrationViewModel
            ) {
                navHostManualRegistration.popBackStack()
            }
        }
    }


}

@Composable
fun ManualRegistrationSelect(
    modifier: Modifier = Modifier,
    manualRegistrationViewModel: ManualRegistrationViewModel,
    manualRegistrationState: ManualRegistrationState,
    navToNewUser: () -> Unit,
    navToVehicleSelection: () -> Unit,
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
            if (manualRegistrationViewModel.personIdSelected != 0.toLong()) {


                CompactFloatingActionButton (
                    icon = Icons.AutoMirrored.Filled.ArrowForward
                ) {
                    navToVehicleSelection()
                }
            }
        }
    ) {
        Box(modifier = modifier.padding(it), contentAlignment = Alignment.Center) {
            SearchListComponent(
                searchLabel = stringResource(id = R.string.search_user_label),
                value = manualRegistrationViewModel.searchPersonInput,
                emptyMsg = stringResource(id = R.string.element_not_found),
                buttonLabel = stringResource(id = R.string.gen_user),
                items = manualRegistrationState.peopleList,
                searchAction = {
                    manualRegistrationViewModel.onSearchPersonInputChanged(it)
                },
                navAction = { navToNewUser() }
            ) {
                PersonaCard(
                    persona = it,
                    selectedItem = manualRegistrationViewModel.personIdSelected
                ) {
                    manualRegistrationViewModel.onPersonSelected(it)
                }
            }
        }
    }

}