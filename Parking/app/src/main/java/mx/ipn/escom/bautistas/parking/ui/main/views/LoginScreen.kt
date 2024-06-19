package mx.ipn.escom.bautistas.parking.ui.main.views

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDefaults
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import mx.ipn.escom.bautistas.parking.R
import mx.ipn.escom.bautistas.parking.ui.components.BalanceUI
import mx.ipn.escom.bautistas.parking.ui.components.ButtonComponent
import mx.ipn.escom.bautistas.parking.ui.components.LoadingDialogComponent
import mx.ipn.escom.bautistas.parking.ui.components.TextFieldComponent
import mx.ipn.escom.bautistas.parking.ui.main.interactions.AuthState
import mx.ipn.escom.bautistas.parking.ui.main.interactions.LoginUiState
import mx.ipn.escom.bautistas.parking.ui.main.viewmodels.AuthViewModel
import mx.ipn.escom.bautistas.parking.ui.main.viewmodels.LoginViewModel

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    windowSizeClass: WindowSizeClass,
    authViewModel: AuthViewModel,
    authState: AuthState,
) {
    val loginViewModel: LoginViewModel = viewModel()
    val loginUiState by loginViewModel.loginUiState.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = authState) {
        if (authState.isError && authState.message != null) {
            snackbarHostState
                .showSnackbar(
                    message = authState.message,
                    duration = SnackbarDuration.Long,
                )
        }
    }

    Scaffold(
        modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { snackbarData ->
                Snackbar (snackbarData = snackbarData, containerColor = colorResource(id = R.color.guinda))
            }
        }
    ) {
        Box(modifier.padding(it)) {
            when {
                windowSizeClass.widthSizeClass == WindowWidthSizeClass.Medium -> CompactLogin(
                    loginViewModel = loginViewModel,
                    authViewModel = authViewModel,
                    loginUiState = loginUiState
                )

                windowSizeClass.widthSizeClass >= WindowWidthSizeClass.Expanded -> ExpandedLogin(
                    loginViewModel = loginViewModel,
                    authViewModel = authViewModel,
                    loginUiState = loginUiState
                )

                else -> CompactLogin(
                    loginViewModel = loginViewModel,
                    authViewModel = authViewModel,
                    loginUiState = loginUiState
                )
            }
            if (authState.isLoading) {
                LoadingDialogComponent()
            }
        }
    }
}


@Composable
fun CompactLogin(
    modifier: Modifier = Modifier,
    padding: Dp = 25.dp,
    loginViewModel: LoginViewModel,
    loginUiState: LoginUiState,
    authViewModel: AuthViewModel,
) {

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = padding)
            .verticalScroll(rememberScrollState()),
    ) {

        Text(
            text = stringResource(id = R.string.labelLogin),
            fontSize = 32.sp,
            color = colorResource(
                id = R.color.guinda
            )
        )
        Spacer(modifier = modifier.height(95.dp))
        TextFieldComponent(
            modifier.fillMaxWidth(),
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Email,
                    contentDescription = "",
                    tint = colorResource(id = R.color.guinda)
                )
            },
            value = loginViewModel.emailInput,
            label = stringResource(id = R.string.email_label)
        ) {
            loginViewModel.onEmailChange(it)
        }
        TextFieldComponent(
            modifier.fillMaxWidth(),
            icon = {
                Icon(
                    imageVector = Icons.Filled.Password,
                    contentDescription = "",
                    tint = colorResource(id = R.color.guinda)
                )
            },
            trailingIcon = {
                IconButton(onClick = {
                    loginViewModel.onPasswordVisibilityChange()
                }) {
                    val icon =
                        if (loginUiState.isVisiblePassword) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff
                    Icon(imageVector = icon, contentDescription = "")
                }
            },
            visualTransformation = if (loginUiState.isVisiblePassword) VisualTransformation.None else PasswordVisualTransformation(),
            value = loginViewModel.passwordInput,
            label = stringResource(id = R.string.password_label)
        ) {
            loginViewModel.onPasswordChange(it)
        }
        Spacer(modifier = modifier.height(90.dp))
        ButtonComponent(
            modifier = modifier.width(250.dp),
            label = stringResource(id = R.string.login_button_label),
            fontSize = 24.sp,
            isEnable = isLoginButtonEnable(loginUiState, loginViewModel)
        ) {
            authViewModel.doLogin(loginViewModel.emailInput, loginViewModel.passwordInput)
        }
    }
}

fun isLoginButtonEnable(loginUiState: LoginUiState, loginViewModel: LoginViewModel): Boolean {
    return loginUiState.isEmailValid && loginViewModel.passwordInput.isNotBlank() && loginViewModel.emailInput.isNotBlank()
}

@Composable
fun ExpandedLogin(
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel,
    loginUiState: LoginUiState,
    authViewModel: AuthViewModel,
) {
    BalanceUI(
        content1 = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo_parking),
                    contentDescription = ""
                )
                Text(
                    text = stringResource(id = R.string.system_label),
                    fontSize = 24.sp,
                    color = colorResource(
                        id = R.color.guinda
                    )
                )
            }

        }, content2 = {
            CompactLogin(
                modifier,
                padding = 90.dp,
                loginViewModel = loginViewModel,
                loginUiState = loginUiState,
                authViewModel,
            )
        }
    )
}


