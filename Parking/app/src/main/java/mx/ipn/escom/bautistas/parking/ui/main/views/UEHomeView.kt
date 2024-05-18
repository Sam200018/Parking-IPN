package mx.ipn.escom.bautistas.parking.ui.main.views

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import mx.ipn.escom.bautistas.parking.R
import mx.ipn.escom.bautistas.parking.ui.components.AccessCardComponent
import mx.ipn.escom.bautistas.parking.ui.components.TopBarComponent
import mx.ipn.escom.bautistas.parking.ui.main.viewmodels.UEViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UEHomeView(modifier: Modifier = Modifier, logout: () -> Unit) {

    val ueViewModel: UEViewModel = hiltViewModel()
    val ueUiState by ueViewModel.ueUiState.collectAsStateWithLifecycle()


    val pagerState = rememberPagerState(
        initialPage = 1,
        pageCount = {
            ueUiState.accessCards.size
        })


    Scaffold(
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
        Box(modifier.padding(it)) {
            Column(modifier.padding(10.dp)) {
                Box(
                    modifier = modifier
                        .height(250.dp)
                        .fillMaxWidth()
                ) {
                    HorizontalPager(
                        state = pagerState,
                        contentPadding = PaddingValues(10.dp),
                        modifier = modifier
                            .fillMaxSize()
                    ) { cardIndex ->
                        AccessCardComponent(
                            accessCard = ueUiState.accessCards[cardIndex],
                            ueViewModel = ueViewModel,
                            ueUiState = ueUiState,
                            pagerState = pagerState,
                            page = cardIndex
                        )

                    }

                }
                Box(modifier = modifier.fillMaxSize()) {
                    Text(text = "hola")
                }
            }
        }
    }

}
