package mx.ipn.escom.bautistas.parking.ui.main.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import mx.ipn.escom.bautistas.parking.R
import mx.ipn.escom.bautistas.parking.ui.components.BalanceUI
import mx.ipn.escom.bautistas.parking.ui.components.DialogComponent
import mx.ipn.escom.bautistas.parking.ui.components.FloatingActionButtonComponent
import mx.ipn.escom.bautistas.parking.ui.components.LoadingDialogComponent
import mx.ipn.escom.bautistas.parking.ui.components.PersonaCard
import mx.ipn.escom.bautistas.parking.ui.components.SearchListComponent
import mx.ipn.escom.bautistas.parking.ui.components.TopBarComponent
import mx.ipn.escom.bautistas.parking.ui.components.VehicleCard
import mx.ipn.escom.bautistas.parking.ui.main.MainActivity
import mx.ipn.escom.bautistas.parking.ui.main.Routes
import mx.ipn.escom.bautistas.parking.ui.main.interactions.GenAccessCardState
import mx.ipn.escom.bautistas.parking.ui.main.viewmodels.GenAccessCardViewModel

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun GenAccessCardScreen(
    modifier: Modifier = Modifier,
    mainActivity: MainActivity,
    navToNewUser: () -> Unit,
    navToNewVehicle: () -> Unit,
    backAction: () -> Unit
) {
    val genAccessCardViewModel: GenAccessCardViewModel = hiltViewModel()
    val genAccessCardState by genAccessCardViewModel.genAccessCardState.collectAsStateWithLifecycle()

    val navHostGenAccessCard = rememberNavController()

    NavHost(
        navController = navHostGenAccessCard,
        startDestination = Routes.GenAccessCardMain.route
    ) {
        composable(Routes.GenAccessCardMain.route) {
            GenAccessCardView(
                genAccessCardViewModel = genAccessCardViewModel,
                genAccessCardState = genAccessCardState,
                navToNewUser = navToNewUser,
                navToNewVehicle = navToNewVehicle,
                navToCompleteUserInfo = {
                    navHostGenAccessCard.navigate("${Routes.CompleteUserInfo.route}/$it")
                }
            ) {
                backAction()
            }
        }
        composable("${Routes.CompleteUserInfo.route}/{id_persona}",
            arguments = listOf(navArgument("id_persona") { type = NavType.LongType })
        ) { backStackEntry ->

            NewAccountUserScreen(mainActivity = mainActivity,
                idPersona = backStackEntry.arguments?.getLong("id_persona")
            ) {
                navHostGenAccessCard.popBackStack()
            }

        }
    }

}

@Composable
fun GenAccessCardView(
    modifier: Modifier = Modifier,
    genAccessCardViewModel: GenAccessCardViewModel,
    genAccessCardState: GenAccessCardState,
    navToNewUser: () -> Unit,
    navToNewVehicle: () -> Unit,
    navToCompleteUserInfo: (String) -> Unit,
    backAction: () -> Unit
) {

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = genAccessCardState.isError) {
        if (genAccessCardState.isError && genAccessCardState.message.isNotEmpty()) {
            snackbarHostState.showSnackbar(
                message = genAccessCardState.message,
                duration = SnackbarDuration.Short
            )
        }
    }


    Scaffold(
        topBar = {
            TopBarComponent(title = stringResource(id = R.string.gen_access_card_label)) {
                backAction()
            }
        },
        floatingActionButton = {
            if (genAccessCardViewModel.personIdSelected != 0.toLong() && genAccessCardViewModel.vehicleIdSelected != 0.toLong()) {
                FloatingActionButtonComponent(
                    icon = Icons.AutoMirrored.Filled.ArrowForward
                ) {
                    genAccessCardViewModel.onGenAccessCard {
                        val idPersona = genAccessCardViewModel.personIdSelected.toString()
                        navToCompleteUserInfo(idPersona)
                    }
                }
            }

        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
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
            if (genAccessCardState.isLoading) {
                LoadingDialogComponent()
            }
            if (genAccessCardState.isSuccess) {
                DialogComponent(
                    message = genAccessCardState.message,
                    icon = Icons.Filled.CheckCircle
                ) {
                    backAction()
                }
            }
        }
    }
}