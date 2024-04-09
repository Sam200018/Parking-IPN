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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import mx.ipn.escom.bautistas.parking.R
import mx.ipn.escom.bautistas.parking.ui.components.BalanceUI
import mx.ipn.escom.bautistas.parking.ui.components.ButtonComponent
import mx.ipn.escom.bautistas.parking.ui.components.TextFieldComponent
import mx.ipn.escom.bautistas.parking.ui.main.viewmodels.AuthViewModel
import mx.ipn.escom.bautistas.parking.ui.main.viewmodels.LoginViewModel

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    windowSizeClass: WindowSizeClass,
    authViewModel: AuthViewModel,
) {
    val loginViewModel: LoginViewModel = viewModel()


    Scaffold(
        modifier.fillMaxSize()
    ) {
        Box(modifier.padding(it)) {
            when {
                windowSizeClass.widthSizeClass == WindowWidthSizeClass.Medium -> CompactLogin(
                    loginViewModel = loginViewModel,
                    authViewModel = authViewModel
                )

                windowSizeClass.widthSizeClass >= WindowWidthSizeClass.Expanded -> ExpandedLogin(
                    loginViewModel = loginViewModel,
                    authViewModel = authViewModel
                )

                else -> CompactLogin(
                    loginViewModel = loginViewModel,
                    authViewModel = authViewModel
                )
            }
        }
    }
}


@Composable
fun CompactLogin(
    modifier: Modifier = Modifier,
    padding: Dp = 25.dp,
    loginViewModel: LoginViewModel,
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
            icon = Icons.Outlined.Email,
            value = loginViewModel.emailInput,
            label = stringResource(id = R.string.email_label)
        ) {
            loginViewModel.onEmailChange(it)
        }
        TextFieldComponent(
            modifier.fillMaxWidth(),
            icon = Icons.Filled.Password,
            value = loginViewModel.passwordInput,
            label = stringResource(id = R.string.password_label)
        ) {
            loginViewModel.onPasswordChange(it)
        }
        Spacer(modifier = modifier.height(90.dp))
        ButtonComponent(
            modifier = modifier.width(250.dp),
            label = stringResource(id = R.string.login_button_label),
            fontSize = 24.sp
        ) {
            Log.i("login S", "hola")
            authViewModel.doLogin(loginViewModel.emailInput, loginViewModel.passwordInput)
        }
    }
}

@Composable
fun ExpandedLogin(
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel,
    authViewModel: AuthViewModel
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
            CompactLogin(modifier, padding = 90.dp, loginViewModel = loginViewModel, authViewModel)
        }
    )
}


