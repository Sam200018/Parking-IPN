package mx.ipn.escom.bautistas.parking.ui.main.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import mx.ipn.escom.bautistas.parking.ui.components.AdminNavRail
import mx.ipn.escom.bautistas.parking.ui.main.interactions.AdminNavState
import mx.ipn.escom.bautistas.parking.ui.main.interactions.AuthState


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    windowSizeClass: WindowSizeClass,
    authState: AuthState,
    hasNFC: Boolean,
    navSelectUser: () -> Unit,
) {

    if (authState.account!!.idRol == 1.toLong()) {
        when {
            windowSizeClass.widthSizeClass == WindowWidthSizeClass.Medium -> compactHome(
                hasNFC = hasNFC,
                authState = authState
            )

            windowSizeClass.widthSizeClass >= WindowWidthSizeClass.Expanded -> ExpandedHome(
                navSelectUser = navSelectUser
            )

            else -> compactHome(hasNFC = hasNFC, authState = authState)
        }
    } else {
        compactHome(hasNFC = hasNFC, authState = authState)
    }


}


@Composable
fun ExpandedHome(
    modifier: Modifier = Modifier,
    navSelectUser: () -> Unit
) {

    var currentSection by remember {
        mutableStateOf(AdminNavState.Prerecord)
    }

    Row(modifier.fillMaxSize()) {
        AdminNavRail(
            adminNavState = currentSection,
            changeContent = {
                currentSection = it
            },
        )
        Box(modifier = modifier.weight(1f, fill = true)) {
            when (currentSection) {
                AdminNavState.Prerecord -> PrerecordView(navSelectUser = navSelectUser)
                AdminNavState.Accounts -> AccountsView()
                AdminNavState.Incidents -> IncidentsView()
            }
        }
    }
}


@Composable
fun compactHome(
    modifier: Modifier = Modifier,
    hasNFC: Boolean,
    authState: AuthState
) {
    Scaffold(
        bottomBar = {
            if (authState.account!!.idRol == 6.toLong()) {
                NavigationBar {

                }
            }
        }
    ) {
        Box(modifier.padding(it), contentAlignment = Alignment.Center) {
            Text(text = "Hoolaa login")
        }

    }
}
