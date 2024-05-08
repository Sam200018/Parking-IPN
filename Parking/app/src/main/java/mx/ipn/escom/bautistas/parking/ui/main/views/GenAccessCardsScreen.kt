package mx.ipn.escom.bautistas.parking.ui.main.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import mx.ipn.escom.bautistas.parking.R
import mx.ipn.escom.bautistas.parking.ui.components.BalanceUI
import mx.ipn.escom.bautistas.parking.ui.components.PersonaCard
import mx.ipn.escom.bautistas.parking.ui.components.SearchListComponent
import mx.ipn.escom.bautistas.parking.ui.components.TopBarComponent
import mx.ipn.escom.bautistas.parking.ui.components.VehicleCard
import mx.ipn.escom.bautistas.parking.ui.main.viewmodels.GenAccessCardViewModel

@Composable
fun GenAccessCardScreen(
    modifier: Modifier = Modifier,
    navToNewUser: () -> Unit,
    navToNewVehicle: () -> Unit
) {
    val genAccessCardViewModel: GenAccessCardViewModel = hiltViewModel()

    val genAccessCardState by genAccessCardViewModel.genAccessCardState.collectAsStateWithLifecycle()


    Scaffold(
        topBar = {
            TopBarComponent(title = stringResource(id = R.string.gen_access_card_label))
        }
    ) {
        Box(modifier = modifier.padding(it)) {
            BalanceUI(
                content1 = {
                    SearchListComponent(
                        searchLabel = stringResource(id = R.string.search_by_name_ipdId_phone),
                        value = genAccessCardViewModel.searchPersonInput,
                        items = genAccessCardState.peopleList,
                        emptyMsg = stringResource(id = R.string.empty_personas_label),
                        buttonLabel = stringResource(id = R.string.gen_persona),
                        searchAction = {
                            genAccessCardViewModel.searchPersonChanged(it)
                        },
                        navAction = navToNewUser
                    ) {
                        PersonaCard(
                            persona = it,
                            selectedItem = genAccessCardViewModel.personIdSelected
                        ) {
                            genAccessCardViewModel.personIdSelectedChanged(it)
                        }
                    }
                }, content2 = {
                    SearchListComponent(
                        searchLabel = stringResource(id = R.string.search_by_plates),
                        value = genAccessCardViewModel.searchVehicleInput,
                        items = genAccessCardState.vehicleList,
                        emptyMsg = stringResource(id = R.string.empty_vehicles_label),
                        buttonLabel = stringResource(id = R.string.gen_vehicle),
                        searchAction = {
                            genAccessCardViewModel.searchVehicleChanged(it)
                        },
                        navAction = navToNewVehicle
                    ) {
                        VehicleCard(
                            vehicle = it,
                            selectedItem = genAccessCardViewModel.vehicleIdSelected
                        ) {
                            genAccessCardViewModel.vehicleIdSelectedChange(it)
                        }
                    }
                }
            )
        }
    }
}