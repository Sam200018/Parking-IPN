package mx.ipn.escom.bautistas.parking.ui.components

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCard
import androidx.compose.material.icons.outlined.AppRegistration
import androidx.compose.material.icons.outlined.EmergencyShare
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Radar
import androidx.compose.material.icons.outlined.SupervisorAccount
import androidx.compose.material.icons.outlined.WarningAmber
import androidx.compose.ui.graphics.vector.ImageVector
import mx.ipn.escom.bautistas.parking.R
import mx.ipn.escom.bautistas.parking.ui.main.interactions.AdminNavState
import mx.ipn.escom.bautistas.parking.ui.main.interactions.VigilantNavState

data class NavigationItem(
    val icon: ImageVector,
    @StringRes val title: Int,
    val adminNavState: AdminNavState? = null,
    val vigilantNavState: VigilantNavState? = null
)

val adminNavigationItems = listOf(
    NavigationItem(Icons.Outlined.AppRegistration, R.string.access_cards, AdminNavState.Prerecord),
    NavigationItem(
        Icons.Outlined.SupervisorAccount,
        R.string.accounts_label,
        AdminNavState.Accounts
    ),
    NavigationItem(Icons.Outlined.WarningAmber, R.string.incidents_label, AdminNavState.Incidents),
)

val vigilantNavigationItems = listOf(
    NavigationItem(
        Icons.Outlined.Home,
        R.string.home_label,
        vigilantNavState = VigilantNavState.Home
    ),
    NavigationItem(
        Icons.Outlined.Radar,
        R.string.scan_label,
        vigilantNavState = VigilantNavState.Scanner
    ),
    NavigationItem(
        Icons.Outlined.EmergencyShare,
        R.string.incidents_report_label,
        vigilantNavState = VigilantNavState.Incidents
    ),
)