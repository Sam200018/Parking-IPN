package mx.ipn.escom.bautistas.parking.ui.main.views

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
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
import mx.ipn.escom.bautistas.parking.ui.components.LoadingDialogComponent
import mx.ipn.escom.bautistas.parking.ui.components.PhotoButton
import mx.ipn.escom.bautistas.parking.ui.components.TextFieldComponent
import mx.ipn.escom.bautistas.parking.ui.components.TopBarComponent
import mx.ipn.escom.bautistas.parking.ui.main.Routes
import mx.ipn.escom.bautistas.parking.ui.main.interactions.NewUserState
import mx.ipn.escom.bautistas.parking.ui.main.viewmodels.NewUserViewModel

@Composable
fun NewUserScreen(
    modifier: Modifier = Modifier,
    backAction: () -> Unit
) {
    val scrollStateNU = rememberScrollState()

    val newUserViewModel: NewUserViewModel = hiltViewModel()
    val newUserState by newUserViewModel.newUserUIState.collectAsStateWithLifecycle()

    val navControllerNUser = rememberNavController()

    NavHost(
        navController = navControllerNUser,
        startDestination = Routes.NewUserMainContent.route
    ) {
        composable(Routes.NewUserMainContent.route) {
            MainContent(
                backAction,
                modifier,
                scrollStateNU,
                newUserViewModel,
                navController = navControllerNUser,
                newUserState = newUserState
            )
        }
        composable(Routes.NewUserCamaraP.route) {
            CamaraScreen(photo = newUserViewModel.personPhoto) {
                newUserViewModel.onPersonPhotoChange(it)
                navControllerNUser.popBackStack()
            }
        }
        composable(Routes.NewUserCamaraI.route) {
            CamaraScreen(photo = newUserViewModel.identificationPhoto) {
                newUserViewModel.onIdentificationPhotoChange(it)
                navControllerNUser.popBackStack()
            }
        }
    }

}

@Composable
private fun MainContent(
    backAction: () -> Unit,
    modifier: Modifier,
    scrollStateNU: ScrollState,
    newUserViewModel: NewUserViewModel,
    navController: NavController,
    newUserState: NewUserState
) {
    Scaffold(
        topBar = {
            TopBarComponent(title = stringResource(R.string.crate_user_label), backAction = {
                backAction()
            })
        }
    ) {
        Box(
            modifier = modifier
                .padding(it)
                .verticalScroll(scrollStateNU)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
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
                    isError = newUserState.isPhoneValid.not(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    errorMessage = "El valor ingresado no es valido"
                ) {
                    newUserViewModel.onPhoneChange(it)
                }
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
                BalanceUI(modifier = modifier.height(240.dp),
                    content1 = {
                        ButtonComponent(
                            fontSize = 18.sp,
                            isCancelBtn = true,
                            label = stringResource(id = R.string.cancel_label)
                        ) {
                            backAction()
                        }

                    }, content2 = {
                        ButtonComponent(
                            modifier = modifier,
                            fontSize = 18.sp,
                            label = stringResource(id = R.string.crate_account_user_label),
                            isEnable = isButtonEnable(newUserState)
                        ) {
                            newUserViewModel.onCreateNewUser()
                        }
                    }
                )
            }
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

private fun isButtonEnable(newUserState: NewUserState) =
    newUserState.isNameValid &&
            newUserState.isPLastNameValid &&
            newUserState.isMLastNameValid &&
            newUserState.isPhoneValid && newUserState.isPersonPhotoTaken
            && newUserState.isIdentificationPhotoTaken && newUserState.isError.not()

@Preview
@Composable
private fun NewUserPrev() {
    NewUserScreen() {}
}