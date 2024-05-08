package mx.ipn.escom.bautistas.parking.ui.main.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
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
import mx.ipn.escom.bautistas.parking.ui.components.academProgOption
import mx.ipn.escom.bautistas.parking.ui.components.typeUserOptions
import mx.ipn.escom.bautistas.parking.ui.main.Routes
import mx.ipn.escom.bautistas.parking.ui.main.viewmodels.NewUserViewModel

@Composable
fun NewUserScreen(
    modifier: Modifier = Modifier,
    backAction: () -> Unit,
) {

    val newUserViewModel: NewUserViewModel = hiltViewModel()


    val navControllerNewUser = rememberNavController()

    NavHost(
        navController = navControllerNewUser,
        startDestination = Routes.NewUserMainContent.route
    ) {
        composable(Routes.NewUserMainContent.route) {
            MainContent(
                navController = navControllerNewUser,
                newUserViewModel = newUserViewModel, backAction = backAction
            )
        }
        composable(Routes.NewUserCamaraP.route) {
            CamaraScreen(photo = newUserViewModel.personPhoto) {
                newUserViewModel.onPersonPhotoChange(it)
                navControllerNewUser.popBackStack()
            }
        }
        composable(Routes.NewUserCamaraI.route) {
            CamaraScreen(photo = newUserViewModel.identificationPhoto) {
                newUserViewModel.onIdentificationPhotoChange(it)
                navControllerNewUser.popBackStack()
            }
        }
    }

}


@Composable
fun MainContent(
    modifier: Modifier = Modifier,
    navController: NavController,
    newUserViewModel: NewUserViewModel,
    backAction: () -> Unit,
) {
    val scrollStateNU = rememberScrollState()

    Scaffold(
        topBar = {
            TopBarComponent(title = stringResource(id = R.string.gen_persona)) {
                backAction()
            }
        }
    ) {
        Box(modifier = modifier.padding(it)) {
            BalanceUI(
                content1 = {
                    Column(
                        modifier
                            .fillMaxSize()
                            .verticalScroll(scrollStateNU)
                            .imePadding()
                    ) {
                        DropDownComponent(
                            items = typeUserOptions,
                            label = stringResource(id = R.string.type_user_label),
                            value = newUserViewModel.userTypeVal
                        ) {
                            newUserViewModel.onUserTypeChanged(it)
                        }
                        Spacer(modifier = modifier.height(20.dp))
                        if (newUserViewModel.userTypeVal != 5)
                            Row(
                                modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                TextFieldComponent(
                                    label = stringResource(id = R.string.ipn_id_label),
                                    value = newUserViewModel.ipnIDVal ?: ""
                                ) {
                                    if (newUserViewModel.userTypeVal != 5) {
                                        newUserViewModel.onIpnIDChanged(it)

                                    } else {
                                        newUserViewModel.onIpnIDChanged(null)

                                    }
                                }
                                if (newUserViewModel.userTypeVal == 2)
                                    DropDownComponent(
                                        items = academProgOption, label = stringResource(
                                            id = R.string.academ_prog_label,
                                        ),
                                        value = newUserViewModel.progAcademicoVal ?: 0
                                    ) {

                                        if (newUserViewModel.userTypeVal == 2) {

                                            newUserViewModel.onProgAcademicoChange(it)
                                        } else {
                                            newUserViewModel.onProgAcademicoChange(null)

                                        }
                                    }
                            }
                        TextFieldComponent(
                            modifier.fillMaxWidth(),
                            label = stringResource(id = R.string.name_label),
                            value = newUserViewModel.nameVal
                        ) {
                            newUserViewModel.onNameChange(it)
                        }
                        TextFieldComponent(
                            modifier.fillMaxWidth(),
                            label = stringResource(id = R.string.last_name_p_label),
                            value = newUserViewModel.pLastNameVal
                        ) {
                            newUserViewModel.onPLastNameChange(it)
                        }
                        TextFieldComponent(
                            modifier.fillMaxWidth(),
                            label = stringResource(id = R.string.last_name_m_label),
                            value = newUserViewModel.mLastNameVal
                        ) {
                            newUserViewModel.onMLastNameChange(it)
                        }
                        TextFieldComponent(
                            modifier.fillMaxWidth(),
                            label = stringResource(id = R.string.phone_label),
                            value = newUserViewModel.phoneVal,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                        ) {
                            newUserViewModel.onPhoneChange(it)
                        }
                        TextFieldComponent(
                            modifier.fillMaxWidth(),
                            label = stringResource(id = R.string.email_label),
                            value = newUserViewModel.emailVal,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                        ) {
                            newUserViewModel.onEmailChange(it)
                        }
                    }
                }, content2 = {
                    Column(
                        modifier
                            .fillMaxSize()
                    ) {
                        PhotoButton(
                            label = stringResource(id = R.string.photo_person_label),
                            icon = Icons.Filled.AddAPhoto,
                            value = newUserViewModel.personPhoto
                        ) {
                            navController.navigate(Routes.NewUserCamaraP.route)
                        }
                        PhotoButton(
                            label = stringResource(id = R.string.photo_id_label),
                            width = 380.dp,
                            icon = Icons.Filled.AddAPhoto,
                            value = newUserViewModel.identificationPhoto,
                            contentScale = ContentScale.FillWidth
                        ) {
                            navController.navigate(Routes.NewUserCamaraI.route)
                        }
                        BalanceUI(modifier = modifier.height(240.dp),
                            content1 = {
                                ButtonComponent(
                                    fontSize = 24.sp,
                                    label = stringResource(id = R.string.cancel_label)
                                ) {
                                    backAction()
                                }

                            }, content2 = {
                                ButtonComponent(
                                    modifier = modifier,
                                    fontSize = 24.sp,
                                    label = stringResource(id = R.string.crate_user_label)
                                ) {
                                    newUserViewModel.onNewUserCreated()
                                }
                            }
                        )
                    }
                }
            )
        }
    }
}