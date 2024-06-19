package mx.ipn.escom.bautistas.parking.ui.main.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import mx.ipn.escom.bautistas.parking.ui.components.AdminNavRail
import mx.ipn.escom.bautistas.parking.ui.main.interactions.AdminNavState
import mx.ipn.escom.bautistas.parking.ui.main.interactions.AuthState
import mx.ipn.escom.bautistas.parking.ui.main.viewmodels.AdminViewModel


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    windowSizeClass: WindowSizeClass,
    authState: AuthState,
    hasNFC: Boolean,
    logout: () -> Unit,
    navSelectUser: () -> Unit,
) {

    if (authState.account?.idRol == 1.toLong()) {
        when {
            windowSizeClass.widthSizeClass == WindowWidthSizeClass.Medium -> compactHome(
                hasNFC = hasNFC,
                authState = authState,
                logout = logout
            )

            windowSizeClass.widthSizeClass >= WindowWidthSizeClass.Expanded -> ExpandedHome(
                navSelectUser = navSelectUser, logout = logout
            )

            else -> compactHome(hasNFC = hasNFC, authState = authState, logout = logout)
        }
    } else {
        compactHome(hasNFC = hasNFC, authState = authState, logout = logout)
    }


}


@Composable
fun ExpandedHome(
    modifier: Modifier = Modifier,
    navSelectUser: () -> Unit,
    logout: () -> Unit
) {

    var currentSection by remember {
        mutableStateOf(AdminNavState.Prerecord)
    }

    val adminViewModel: AdminViewModel = hiltViewModel()
    val adminUiState by adminViewModel.adminUiState.collectAsStateWithLifecycle()


    Row(modifier.fillMaxSize()) {
        AdminNavRail(
            adminNavState = currentSection,
            changeContent = {
                currentSection = it
            },
        )
        Box(modifier = modifier.weight(1f, fill = true)) {
            when (currentSection) {
                AdminNavState.Prerecord -> AccessCardsView(navSelectUser = navSelectUser, logout = logout)
                AdminNavState.Accounts -> AccountsView()
                AdminNavState.Incidents -> IncidentsView()
            }
        }
    }
}


@Composable
fun compactHome(
    modifier: Modifier = Modifier,
    logout: () -> Unit,
    hasNFC: Boolean,
    authState: AuthState
) {
    authState.account?.let { cuenta ->
        if (cuenta.idRol == 6.toLong()) {
            VigilanteHomeView(logout = logout)
        } else {
            UEHomeView(logout = logout)
        }
    }
}
