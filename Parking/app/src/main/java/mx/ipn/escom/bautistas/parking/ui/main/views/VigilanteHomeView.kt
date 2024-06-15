package mx.ipn.escom.bautistas.parking.ui.main.views

import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import mx.ipn.escom.bautistas.parking.R
import mx.ipn.escom.bautistas.parking.ui.components.IconNavItem
import mx.ipn.escom.bautistas.parking.ui.components.LoadingDialogComponent
import mx.ipn.escom.bautistas.parking.ui.components.TopBarComponent
import mx.ipn.escom.bautistas.parking.ui.components.vigilantNavigationItems
import mx.ipn.escom.bautistas.parking.ui.main.interactions.VigilantNavState
import mx.ipn.escom.bautistas.parking.ui.main.viewmodels.VigilanteViewModel
import mx.ipn.escom.bautistas.parking.ui.reader.ReaderActivity


@Composable
fun VigilanteHomeView(modifier: Modifier = Modifier, logout: () -> Unit) {
    val vigilanteViewModel: VigilanteViewModel = hiltViewModel()
    val vigilanteUiState by vigilanteViewModel.vigilanteUiState.collectAsStateWithLifecycle()

    val context = LocalContext.current

    var currentSection by remember {
        mutableStateOf(VigilantNavState.Home)
    }

    Scaffold(
        bottomBar = {
            NavigationBar {
                vigilantNavigationItems.forEach { item ->
                    NavigationBarItem(
                        selected = item.vigilantNavState == currentSection,
                        onClick = {
                            if (item.vigilantNavState == VigilantNavState.Scanner) {
                                val intent = Intent(context, ReaderActivity::class.java)
                                context.startActivity(intent)
                            } else {
                                currentSection = item.vigilantNavState!!
                            }
                        },
                        icon = {
                            IconNavItem(
                                icon = item.icon,
                                title = item.title,
                                size = 20.dp,
                                color = R.color.guinda
                            )
                        },
                    )
                }
            }
        },
        topBar = {
            TopBarComponent(title = stringResource(id = R.string.top_title),
                actions = {
                    IconButton(onClick = logout) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Logout,
                            contentDescription = "",
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) {
        Box(modifier.padding(it), contentAlignment = Alignment.Center) {
            when(currentSection){
                VigilantNavState.Home -> RecordsView(vigilanteUiState = vigilanteUiState)
                VigilantNavState.Scanner -> TODO()
                VigilantNavState.Incidents -> TODO()
            }
        }

        if (vigilanteUiState.isLoading) {
            LoadingDialogComponent()
        }
    }
}