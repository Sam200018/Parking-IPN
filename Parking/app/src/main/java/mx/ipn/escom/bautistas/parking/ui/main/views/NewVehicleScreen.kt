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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.DocumentScanner
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import mx.ipn.escom.bautistas.parking.R
import mx.ipn.escom.bautistas.parking.ui.components.BalanceUI
import mx.ipn.escom.bautistas.parking.ui.components.ButtonComponent
import mx.ipn.escom.bautistas.parking.ui.components.DialogComponent
import mx.ipn.escom.bautistas.parking.ui.components.DocPhotoButton
import mx.ipn.escom.bautistas.parking.ui.components.DropDownComponent
import mx.ipn.escom.bautistas.parking.ui.components.LoadingDialogComponent
import mx.ipn.escom.bautistas.parking.ui.components.PhotoButton
import mx.ipn.escom.bautistas.parking.ui.components.TextFieldComponent
import mx.ipn.escom.bautistas.parking.ui.components.TopBarComponent
import mx.ipn.escom.bautistas.parking.ui.components.vehicleTypeOption
import mx.ipn.escom.bautistas.parking.ui.main.MainActivity
import mx.ipn.escom.bautistas.parking.ui.main.Routes
import mx.ipn.escom.bautistas.parking.ui.main.interactions.VehicleState
import mx.ipn.escom.bautistas.parking.ui.main.viewmodels.NewVehicleViewModel

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun NewVehicleScreen(
    modifier: Modifier = Modifier,
    windowSizeClass: WindowSizeClass,
    mainActivity: MainActivity,
    backAction: () -> Unit
) {

    val newVehicleViewModel: NewVehicleViewModel = hiltViewModel()
    val newVehicleUiState by newVehicleViewModel.newVehicleUiState.collectAsStateWithLifecycle()

    val navControllerNewVehicle = rememberNavController()

    NavHost(
        navController = navControllerNewVehicle,
        startDestination = Routes.NewVehicleMainContent.route
    ) {
        composable(Routes.NewVehicleMainContent.route) {
            MainContentNewVehicle(
                navController = navControllerNewVehicle,
                newVehicleViewModel = newVehicleViewModel,
                vehicleState = newVehicleUiState,
                windowSizeClass = windowSizeClass,
                mainActivity = mainActivity,
            ) {
                backAction()
            }
        }
    }


}


@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun MainContentNewVehicle(
    modifier: Modifier = Modifier,
    navController: NavController,
    newVehicleViewModel: NewVehicleViewModel,
    vehicleState: VehicleState,
    windowSizeClass: WindowSizeClass,
    mainActivity: MainActivity,
    backAction: () -> Unit,
) {
    Scaffold(topBar = {
        TopBarComponent(title = stringResource(id = R.string.create_vehicle_label)) {
            backAction()
        }
    }) {
        Box(modifier = modifier.padding(it)) {
            when {
                windowSizeClass.widthSizeClass == WindowWidthSizeClass.Medium -> NewVehicleCompactView(
                    newVehicleViewModel = newVehicleViewModel,
                    vehicleState = vehicleState,
                    navController = navController,
                    mainActivity = mainActivity,
                    backAction = backAction
                )

                windowSizeClass.widthSizeClass >= WindowWidthSizeClass.Expanded -> NewVehicleExpandedView(
                    modifier = modifier,
                    newVehicleViewModel = newVehicleViewModel,
                    vehicleState = vehicleState,
                    mainActivity = mainActivity,
                    backAction = backAction
                )

                else -> NewVehicleCompactView(
                    newVehicleViewModel = newVehicleViewModel,
                    vehicleState = vehicleState,
                    navController = navController,
                    mainActivity = mainActivity,
                    backAction = backAction
                )
            }

            if (vehicleState.isLoading) {
                LoadingDialogComponent()
            }
            if (vehicleState.isSuccess) {
                DialogComponent(message = vehicleState.message, icon = Icons.Filled.CheckCircle) {
                    backAction()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
private fun NewVehicleExpandedView(
    modifier: Modifier,
    newVehicleViewModel: NewVehicleViewModel,
    vehicleState: VehicleState,
    mainActivity: MainActivity,
    backAction: () -> Unit
) {
    BalanceUI(
        content1 = {
            Column(modifier.fillMaxHeight()) {
                DropDownComponent(
                    items = vehicleTypeOption,
                    label = stringResource(id = R.string.type_vehicle_label),
                    value = newVehicleViewModel.vehicleTypeVal,
                    isError = vehicleState.isTypeVehicleSelected.not(),
                    errorMessage = "Debe seleccionarse el tipo de vehiculo"
                ) {
                    newVehicleViewModel.onVehicleTypeChange(it)
                }
                Row(
                    modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextFieldComponent(
                        label = stringResource(id = R.string.plate_label),
                        value = newVehicleViewModel.plateVal,
                        keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Characters),
                        isError = vehicleState.isPlateValid.not(),
                        errorMessage = "Ingresa una placa valida"
                    ) {
                        newVehicleViewModel.onPlateChange(it)
                    }
                    TextFieldComponent(
                        label = stringResource(id = R.string.model_label),
                        value = newVehicleViewModel.modelVal,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isError = vehicleState.isModelValid.not(),
                        errorMessage = "Ingresa un modelo valido"
                    ) {
                        newVehicleViewModel.onModelChange(it)
                    }
                }
                Row(
                    modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextFieldComponent(
                        label = stringResource(id = R.string.brand_label),
                        value = newVehicleViewModel.brandVal,
                        keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
                        isError = vehicleState.isBrandValid.not(),
                        errorMessage = "Ingresa una marca valida"
                    ) {
                        newVehicleViewModel.onBrandChange(it)
                    }
                    TextFieldComponent(
                        label = stringResource(id = R.string.color_label),
                        keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
                        value = newVehicleViewModel.colorVal,
                        isError = vehicleState.isColorValid.not(),
                        errorMessage = "Ingresa un color valido"
                    ) {
                        newVehicleViewModel.onColorChange(it)
                    }
                }
            }
        },
        content2 = {
            Column(
                modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DocPhotoButton(
                    value = newVehicleViewModel.documentPhoto,
                    mainActivity = mainActivity,
                    label = stringResource(id = R.string.document_label),
                    icon = Icons.Filled.DocumentScanner,
                ) { uri, ctx ->
                    newVehicleViewModel.onDocumentPhotoChange(uri, ctx)
                }
                if (vehicleState.isError && vehicleState.message.isNotEmpty()) {
                    Text(text = vehicleState.message, color = Color.Red, fontSize = 20.sp)
                    Text(
                        text = "Corregir y volver a intentar",
                        color = Color.Red,
                        fontSize = 20.sp
                    )
                }
                BalanceUI(modifier = modifier.height(240.dp),
                    content1 = {
                        ButtonComponent(
                            fontSize = 24.sp,
                            isCancelBtn = true,
                            label = stringResource(id = R.string.cancel_label)
                        ) {
                            backAction()
                        }

                    }, content2 = {
                        ButtonComponent(
                            modifier = modifier,
                            fontSize = 24.sp,
                            label = stringResource(id = R.string.create_vehicle_button),
                            isEnable = isButtonNewVehicleEnable(vehicleState)
                        ) {
                            newVehicleViewModel.onNewVehicleCreated()
                        }
                    }
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun NewVehicleCompactView(
    modifier: Modifier = Modifier,
    padding: Dp = 25.dp,
    newVehicleViewModel: NewVehicleViewModel,
    vehicleState: VehicleState,
    navController: NavController,
    mainActivity: MainActivity,
    backAction: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start,
        modifier = modifier
            .fillMaxSize()
            .padding(padding)
            .verticalScroll(rememberScrollState()),
    ) {
        DropDownComponent(
            items = vehicleTypeOption,
            label = stringResource(id = R.string.type_vehicle_label),
            value = newVehicleViewModel.vehicleTypeVal,
            isError = vehicleState.isTypeVehicleSelected.not(),
            errorMessage = "Debe seleccionarse el tipo de vehiculo"
        ) {
            newVehicleViewModel.onVehicleTypeChange(it)
        }
        TextFieldComponent(
            modifier = modifier.fillMaxWidth(),
            label = stringResource(id = R.string.plate_label),
            value = newVehicleViewModel.plateVal,
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Characters),
            isError = vehicleState.isPlateValid.not(),
            errorMessage = "Ingresa una placa valida"
        ) {
            newVehicleViewModel.onPlateChange(it)
        }
        TextFieldComponent(
            modifier = modifier.fillMaxWidth(),
            label = stringResource(id = R.string.model_label),
            value = newVehicleViewModel.modelVal,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = vehicleState.isModelValid.not(),
            errorMessage = "Ingresa un modelo valido"
        ) {
            newVehicleViewModel.onModelChange(it)
        }

        TextFieldComponent(
            modifier = modifier.fillMaxWidth(),
            label = stringResource(id = R.string.brand_label),
            value = newVehicleViewModel.brandVal,
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
            isError = vehicleState.isBrandValid.not(),
            errorMessage = "Ingresa una marca valida"
        ) {
            newVehicleViewModel.onBrandChange(it)
        }
        TextFieldComponent(
            modifier = modifier.fillMaxWidth(),
            label = stringResource(id = R.string.color_label),
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
            value = newVehicleViewModel.colorVal,
            isError = vehicleState.isColorValid.not(),
            errorMessage = "Ingresa un color valido"
        ) {
            newVehicleViewModel.onColorChange(it)
        }
        DocPhotoButton(
            value = newVehicleViewModel.documentPhoto,
            mainActivity = mainActivity,
            label = stringResource(id = R.string.document_label),
            icon = Icons.Filled.DocumentScanner,
        ) { uri, ctx ->
            newVehicleViewModel.onDocumentPhotoChange(uri, ctx)
        }
        if (vehicleState.isError && vehicleState.message.isNotEmpty()) {
            Text(text = vehicleState.message, color = Color.Red, fontSize = 20.sp)
            Text(
                text = "Corregir y volver a intentar",
                color = Color.Red,
                fontSize = 20.sp
            )
        }
        BalanceUI(modifier = modifier.height(240.dp),
            content1 = {
                ButtonComponent(
                    fontSize = 18.sp,
                    isCancelBtn = true,
                    label = stringResource(id = R.string.cancel_label)
                ) {
                }

            }, content2 = {
                ButtonComponent(
                    modifier = modifier,
                    fontSize = 18.sp,
                    label = stringResource(id = R.string.create_vehicle_button),
                    isEnable = isButtonNewVehicleEnable(vehicleState)
                ) {
                    newVehicleViewModel.onNewVehicleCreated()
                }
            }
        )
    }
}

fun isButtonNewVehicleEnable(vehicleState: VehicleState): Boolean =
    vehicleState.isTypeVehicleSelected && vehicleState.isPlateValid &&
            vehicleState.isBrandValid && vehicleState.isColorValid &&
            vehicleState.isBrandValid && vehicleState.isDocumentTaken &&
            vehicleState.isError.not()

