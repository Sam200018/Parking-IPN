package mx.ipn.escom.bautistas.parking.ui.main.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import mx.ipn.escom.bautistas.parking.R
import mx.ipn.escom.bautistas.parking.ui.components.BalanceUI
import mx.ipn.escom.bautistas.parking.ui.components.ButtonComponent
import mx.ipn.escom.bautistas.parking.ui.components.DropDownComponent
import mx.ipn.escom.bautistas.parking.ui.components.PhotoButton
import mx.ipn.escom.bautistas.parking.ui.components.TextFieldComponent
import mx.ipn.escom.bautistas.parking.ui.components.TopBarComponent
import mx.ipn.escom.bautistas.parking.ui.components.vehicleTypeOption
import mx.ipn.escom.bautistas.parking.ui.main.Routes
import mx.ipn.escom.bautistas.parking.ui.main.viewmodels.NewVehicleViewModel

@Composable
fun NewVehicleScreen(modifier: Modifier = Modifier) {

    val newVehicleViewModel: NewVehicleViewModel = hiltViewModel()

    val navControllerNewVehicle = rememberNavController()

    NavHost(
        navController = navControllerNewVehicle,
        startDestination = Routes.NewVehicleMainContent.route
    ) {
        composable(Routes.NewVehicleMainContent.route) {
            MainContentNewVehicle(
                navController = navControllerNewVehicle,
                newVehicleViewModel = newVehicleViewModel
            )
        }
        composable(Routes.NewUserCamaraP.route) {
            CamaraScreen(photo = newVehicleViewModel.documentPhoto) {
                newVehicleViewModel.onDocumentPhotoChange(it)
                navControllerNewVehicle.popBackStack()
            }
        }
    }


}


@Composable
fun MainContentNewVehicle(
    modifier: Modifier = Modifier,
    navController: NavController,
    newVehicleViewModel: NewVehicleViewModel
) {
    Scaffold(topBar = {
        TopBarComponent(title = stringResource(id = R.string.create_vehicle_label))
    }) {
        Box(modifier = modifier.padding(it)) {
            BalanceUI(
                content1 = {
                    Column(modifier.fillMaxHeight()) {
                        DropDownComponent(
                            items = vehicleTypeOption,
                            label = stringResource(id = R.string.type_vehicle_label),
                            value = newVehicleViewModel.vehicleTypeVal
                        ) {

                        }
                        Row(
                            modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            TextFieldComponent(
                                label = stringResource(id = R.string.plate_label),
                                value = newVehicleViewModel.plateVal

                            ) {

                            }
                            TextFieldComponent(
                                label = stringResource(id = R.string.model_label),
                                value = newVehicleViewModel.modelVal
                            ) {

                            }
                        }
                        Row(
                            modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            TextFieldComponent(
                                label = stringResource(id = R.string.brand_label),
                                value = newVehicleViewModel.brandVal
                            ) {

                            }
                            TextFieldComponent(
                                label = stringResource(id = R.string.color_label),
                                value = newVehicleViewModel.colorVal
                            ) {

                            }
                        }
                    }
                },
                content2 = {
                    Column(modifier.fillMaxSize()) {
                        PhotoButton(
                            modifier = modifier
                                .fillMaxWidth()
                                .height(500.dp),
                            label = stringResource(id = R.string.document_label),
                            icon = Icons.Filled.Description,

                        ) {
                            navController.navigate(Routes.NewUserCamaraP.route)
                        }
                        BalanceUI(modifier = modifier.height(240.dp),
                            content1 = {
                                ButtonComponent(
                                    fontSize = 24.sp,
                                    label = stringResource(id = R.string.cancel_label)
                                ) {
                                }

                            }, content2 = {
                                ButtonComponent(
                                    modifier = modifier,
                                    fontSize = 24.sp,
                                    label = stringResource(id = R.string.crate_user_label)
                                ) {
                                }
                            }
                        )
                    }
                }
            )
        }
    }
}

@Preview(showSystemUi = true, device = Devices.PIXEL_TABLET)
@Composable
private fun NewVehiclePrev() {
    NewVehicleScreen()
}