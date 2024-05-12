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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
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
import mx.ipn.escom.bautistas.parking.ui.components.DropDownComponent
import mx.ipn.escom.bautistas.parking.ui.components.LoadingDialogComponent
import mx.ipn.escom.bautistas.parking.ui.components.PhotoButton
import mx.ipn.escom.bautistas.parking.ui.components.TextFieldComponent
import mx.ipn.escom.bautistas.parking.ui.components.TopBarComponent
import mx.ipn.escom.bautistas.parking.ui.components.academProgOption
import mx.ipn.escom.bautistas.parking.ui.components.typeUserOptions
import mx.ipn.escom.bautistas.parking.ui.main.Routes
import mx.ipn.escom.bautistas.parking.ui.main.interactions.NewUserState
import mx.ipn.escom.bautistas.parking.ui.main.viewmodels.NewUserViewModel

@Composable
fun NewUserScreen(
    modifier: Modifier = Modifier,
    idPersona: Long? = null,
    backAction: () -> Unit,
) {

    val newUserViewModel: NewUserViewModel = hiltViewModel()
    val newUserUiState by newUserViewModel.newUserUiState.collectAsStateWithLifecycle()

    val navControllerNewUser = rememberNavController()

    if (idPersona != null) {
        newUserViewModel.loadPersonaInfo(idPersona)
    }



    NavHost(
        navController = navControllerNewUser,
        startDestination = Routes.NewUserMainContent.route
    ) {
        composable(Routes.NewUserMainContent.route) {
            MainContent(
                idPersona = idPersona,
                navController = navControllerNewUser,
                newUserViewModel = newUserViewModel,
                newUserState = newUserUiState
            ) {
                backAction()
            }
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
    idPersona: Long? = null,
    navController: NavController,
    newUserViewModel: NewUserViewModel,
    newUserState: NewUserState,
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
                            value = newUserViewModel.userTypeVal,
                            isError = newUserState.isTypeUserSelected.not(),
                            errorMessage = "Debe seleccionarse el tipo de usuario"
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
                                    value = newUserViewModel.ipnIDVal ?: "",
                                    isError = newUserState.isIPNIdValid.not(),
                                    errorMessage = "Favor de ingresar numero de empleado o boleta"
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
                                        value = newUserViewModel.progAcademicoVal ?: 0,
                                        isError = newUserState.isAcademProgSelected.not()
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
                            value = newUserViewModel.nameVal,
                            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
                            isError = newUserState.isNameValid.not(),
                            errorMessage = "Este campo no puede ir vacio"
                        ) {
                            newUserViewModel.onNameChange(it)
                        }
                        TextFieldComponent(
                            modifier.fillMaxWidth(),
                            label = stringResource(id = R.string.last_name_p_label),
                            value = newUserViewModel.pLastNameVal,
                            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
                            isError = newUserState.isPLastNameValid.not(),
                            errorMessage = "Este campo no puede ir vacio"
                        ) {
                            newUserViewModel.onPLastNameChange(it)
                        }
                        TextFieldComponent(
                            modifier.fillMaxWidth(),
                            label = stringResource(id = R.string.last_name_m_label),
                            value = newUserViewModel.mLastNameVal,
                            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
                            isError = newUserState.isMLastNameValid.not(),
                            errorMessage = "Este campo no puede ir vacio"
                        ) {
                            newUserViewModel.onMLastNameChange(it)
                        }
                        TextFieldComponent(
                            modifier.fillMaxWidth(),
                            label = stringResource(id = R.string.phone_label),
                            value = newUserViewModel.phoneVal,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                            isError = newUserState.isPhoneValid.not(),
                            errorMessage = "El valor ingresado no es valido"
                        ) {
                            newUserViewModel.onPhoneChange(it)
                        }
                        TextFieldComponent(
                            modifier.fillMaxWidth(),
                            label = stringResource(id = R.string.email_label),
                            value = newUserViewModel.emailVal,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            isError = newUserState.isEmailValid.not(),
                            errorMessage = "El valor ingresado no es valido"
                        ) {
                            newUserViewModel.onEmailChange(it)
                        }
                    }
                }, content2 = {
                    Column(
                        modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        if (idPersona != null) {
                            PhotoButton(
                                modifier = modifier.height(250.dp),
                                label = stringResource(id = R.string.photo_person_label),
                                icon = Icons.Filled.AddAPhoto,
                                filename = newUserViewModel.personPhotoRoute
                            ) {
                                navController.navigate(Routes.NewUserCamaraP.route)
                            }
                            PhotoButton(
                                modifier = modifier.height(250.dp),
                                label = stringResource(id = R.string.photo_id_label),
                                width = 380.dp,
                                icon = Icons.Filled.AddAPhoto,
                                filename = newUserViewModel.identificationPhotoRoute,
                                contentScale = ContentScale.FillWidth
                            ) {
                                navController.navigate(Routes.NewUserCamaraI.route)
                            }
                        } else {
                            PhotoButton(
                                modifier = modifier.height(250.dp),
                                label = stringResource(id = R.string.photo_person_label),
                                icon = Icons.Filled.AddAPhoto,
                                value = newUserViewModel.personPhoto
                            ) {
                                navController.navigate(Routes.NewUserCamaraP.route)
                            }
                            PhotoButton(
                                modifier = modifier.height(250.dp),
                                label = stringResource(id = R.string.photo_id_label),
                                width = 380.dp,
                                icon = Icons.Filled.AddAPhoto,
                                value = newUserViewModel.identificationPhoto,
                                contentScale = ContentScale.FillWidth
                            ) {
                                navController.navigate(Routes.NewUserCamaraI.route)
                            }
                        }
                        if (newUserState.isError && newUserState.message.isNotEmpty()) {
                            Text(text = newUserState.message, color = Color.Red, fontSize = 20.sp)
                            Text(
                                text = "Corregir y volver a intentar",
                                color = Color.Red,
                                fontSize = 20.sp
                            )
                        }
                        if (idPersona != null) {
                            Text(
                                text = "Completa los datos faltantes para continuar con la tarjeta de acceso",
                                color = Color.Red,
                                fontSize = 20.sp
                            )
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
                                    label = stringResource(id = R.string.crate_user_label),
                                    isEnable = isButtonEnable(newUserState)
                                ) {
                                    if (idPersona != null) {
                                        newUserViewModel.onCreateAccount(idPersona)
                                    } else {
                                        newUserViewModel.onNewUserCreated()
                                    }
                                }
                            }
                        )
                    }
                }
            )
            if (newUserState.isLoading) {
                LoadingDialogComponent()
            }
            if (newUserState.isSuccess) {
                DialogComponent(message = newUserState.message, icon = Icons.Filled.CheckCircle) {
                    backAction()
                }
            }
        }
    }
}

fun isButtonEnable(newUserState: NewUserState): Boolean =
    newUserState.isTypeUserSelected && newUserState.isIPNIdValid
            && newUserState.isAcademProgSelected && newUserState.isNameValid
            && newUserState.isPLastNameValid && newUserState.isMLastNameValid
            && newUserState.isPhoneValid && newUserState.isEmailValid
            && newUserState.isPersonPhotoTaken && newUserState.isIdentificationPhotoTaken
            && newUserState.isError.not()